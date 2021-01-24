package com.poogle.phog.service;

import com.poogle.phog.domain.*;
import com.poogle.phog.exception.AuthorizationException;
import com.poogle.phog.exception.NotFoundException;
import com.poogle.phog.exception.VerificationException;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteService {

    private NoteRepository noteRepository;
    private TagRepository tagRepository;
    private UserRepository userRepository;
    private NoteTagService noteTagService;

    public NoteService(NoteRepository noteRepository, TagRepository tagRepository, UserRepository userRepository, NoteTagService noteTagService) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.noteTagService = noteTagService;
    }

    @Transactional
    public void save(PostNoteRequestDTO noteRequestDTO, Long userId) {
        LocalDateTime now = LocalDateTime.now();

        if (noteRequestDTO.getRawMemo() == null || noteRequestDTO.getRawMemo().isEmpty() ||
                noteRequestDTO.getPhotos() == null || noteRequestDTO.getPhotos().isEmpty()) {
            throw new VerificationException("Note and Photos can't be blank");
        }

        Note note = Note.builder()
                .user(userRepository.findUserById(userId))
                .rawMemo(noteRequestDTO.getRawMemo())
                .created(now)
                .isDeleted(false)
                .build();
        note.addPhotos(noteRequestDTO.getPhotos());

        List<String> newTags = note.captureTags(note);
        if (newTags.isEmpty()) {
            throw new VerificationException("There should be at least one tag");
        }
        log.debug("[*] newTags : {}", newTags.toString());
        Map<String, Tag> dbTags = tagRepository.findTagsByUserIdAndTagNameIn(userId, newTags)
                .stream()
                .collect(Collectors.toMap(Tag::getTagName, Function.identity()));
        log.debug("[*] dbTags : {}", dbTags.toString());

        checkRemainTag(userId, note, newTags, dbTags);
    }

    private void checkRemainTag(Long userId, Note note, List<String> newTags, Map<String, Tag> dbTags) {
        for (String tagName : newTags) {
            Tag tag;
            if (dbTags.containsKey(tagName)) {
                tag = dbTags.get(tagName);
            } else {
                tag = Tag.builder()
                        .tagName(tagName)
                        .activated(true)
                        .userId(userId)
                        .build();
            }
            NoteTag noteTag = NoteTag.builder()
                    .tag(tag)
                    .note(note)
                    .build();
            note.getNoteTags().add(noteTag);
        }
        noteRepository.save(note);
    }

    public Note findNote(Long noteId) throws NotFoundException {
        return noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException("Note doesn't exist"));
    }

    public List<GetNoteResponseDTO> findAllNotes(Long userId, String word) throws NotFoundException {
        List<GetNoteResponseDTO> searchedNotes = new ArrayList<>();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw AuthorizationException.accessWrong();
        }
        List<Note> notes = noteRepository.findNotesByUserAndRawMemoContainsOrderByCreatedDesc(user, word);
        for (Note note : notes) {
            Long noteId = note.getId();
            GetNoteResponseDTO searchedNote = GetNoteResponseDTO.builder()
                    .noteId(noteId)
                    .tags(findTags(noteId))
                    .created(note.getCreated())
                    .rawMemo(note.getRawMemo())
                    .photos(findPhotos(noteId))
                    .build();
            searchedNotes.add(searchedNote);
        }
        return searchedNotes;

    }

    public List<String> findPhotos(Long noteId) throws NotFoundException {
        Note note = findNote(noteId);
        List<Photo> photos = note.getPhotos();
        if (!photos.isEmpty()) {
            throw new NotFoundException("Photos don't exist");
        }
        List<String> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(photo.getUrl());
        }
        log.debug("[*] photoUrls : {}", photoUrls);
        return photoUrls;
    }

    public List<String> findTags(Long noteId) {
        List<Tag> tags = noteTagService.findTagsByNoteId(noteId);
        if (tags.isEmpty()) {
            throw new NotFoundException("Tags don't exist");
        } else {
            List<String> tagNames = new ArrayList<>();
            for (Tag tag : tags) {
                tagNames.add(tag.getTagName());
            }
            log.debug("[*] tagList : {}", tagNames);
            return tagNames;
        }
    }

    public void edit(Long userId, Long noteId, PostNoteRequestDTO noteDTO) throws NotFoundException {
        LocalDateTime now = LocalDateTime.now();
        if (noteDTO.getRawMemo() == null) {
            throw new VerificationException("Note can't be blank");
        }
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException("Note doesn't exist"));

        note.setCreated(now);
        note.setRawMemo(noteDTO.getRawMemo());

        noteTagService.deleteByNoteId(noteId);

        List<String> newTags = note.captureTags(note);

        Map<String, Tag> dbTags = tagRepository.findTagsByUserIdAndTagNameIn(userId, newTags)
                .stream()
                .collect(Collectors.toMap(Tag::getTagName, Function.identity()));
        log.debug("[*] dbTags: {}", dbTags.toString());

        checkRemainTag(userId, note, newTags, dbTags);
    }

    public void delete(Long noteId) {
        noteRepository.deleteById(noteId);
    }
}
