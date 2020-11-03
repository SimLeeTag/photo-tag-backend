package com.poogle.phog.service;

import com.poogle.phog.domain.*;
import com.poogle.phog.exception.VerificationException;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public NoteService(NoteRepository noteRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    //TODO: userId 추가
    @Transactional
    public void save(PostNoteRequestDTO noteRequestDTO) {
        LocalDateTime now = LocalDateTime.now();

        if (noteRequestDTO.getRawMemo() == null || noteRequestDTO.getPhotos() == null) {
            throw new VerificationException("Note can't be blank");
        }

        Note note = Note.builder()
                .user(userRepository.findUserById(noteRequestDTO.getUserId()))
                .rawMemo(noteRequestDTO.getRawMemo())
                .created(now)
                .isDeleted(false)
                .build();
        note.addPhotos(noteRequestDTO.getPhotos());

        //TODO: Apple Login 반영 후 header로 변경해야 함
        Long userId = noteRequestDTO.getUserId();

        List<String> usedTags = note.captureTags(note);
        log.debug("[*] usedTags : {}", usedTags.toString());
        Map<String, Tag> dbTags = tagRepository.findTagsByUserIdAndTagNameIn(userId, usedTags)
                .stream()
                .collect(Collectors.toMap(Tag::getTagName, Function.identity()));
        log.debug("[*] dbTags : {}", dbTags.toString());

        for (String tagName : usedTags) {
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
}
