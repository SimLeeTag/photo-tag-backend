package com.poogle.phog.service;

import com.poogle.phog.domain.*;
import com.poogle.phog.exception.VerificationException;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void save(PostNoteRequestDTO noteRequestDTO) {
        LocalDateTime now = LocalDateTime.now();

        if (noteRequestDTO.getRawMemo() == null || noteRequestDTO.getPhotos() == null) {
            throw new VerificationException("Note can't be blank");
        }

        //TODO: user 추가
        Note note = Note.builder()
                .rawMemo(noteRequestDTO.getRawMemo())
                .created(now)
                .isDeleted(false)
                .build();
        note.addPhotos(noteRequestDTO.getPhotos());

        List<String> tags = note.captureTags(note);
        log.debug("[*] tags : {}", tags.toString());

        //TODO: userId 추가
        for (String tagName : tags) {
            Optional<Tag> optionalTag = tagRepository.findTagByTagName(tagName);
            Tag tag = optionalTag.orElseGet(() ->
                    Tag.builder()
                            .tagName(tagName)
                            .activated(true)
                            .build()
            );
            NoteTag noteTag = NoteTag.builder()
                    .tag(tag)
                    .note(note)
                    .build();
            note.getNoteTags().add(noteTag);
        }
        noteRepository.save(note);
    }
}
