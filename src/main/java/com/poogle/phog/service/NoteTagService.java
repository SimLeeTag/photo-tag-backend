package com.poogle.phog.service;

import com.poogle.phog.domain.NoteTagRepository;
import com.poogle.phog.domain.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteTagService {

    private NoteTagRepository noteTagRepository;

    public NoteTagService(NoteTagRepository noteTagRepository) {
        this.noteTagRepository = noteTagRepository;
    }

    public List<Tag> findTagsByNoteId(Long noteId) {
        return noteTagRepository.findTagByNoteId(noteId);
    }

    @Transactional
    public void deleteByNoteId(Long noteId) {
        noteTagRepository.deleteByNoteId(noteId);
    }
}
