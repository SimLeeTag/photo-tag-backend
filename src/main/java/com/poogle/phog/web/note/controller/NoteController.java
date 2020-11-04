package com.poogle.phog.web.note.controller;

import com.poogle.phog.domain.Note;
import com.poogle.phog.service.NoteService;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("/notes")
@RestController
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    //TODO: `@RequestAttribute`로 userId 추가
    @PostMapping("")
    public void create(@RequestBody PostNoteRequestDTO request,
                       HttpServletResponse response) {
        noteService.save(request);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @GetMapping("/{note-id}")
    public GetNoteResponseDTO detail(@PathVariable(name = "note-id") Long noteId) throws NotFound {
        Note note = noteService.findNote(noteId);

        GetNoteResponseDTO detail = GetNoteResponseDTO.builder()
                .noteId(noteId)
                .rawMemo(note.getRawMemo())
                .photos(noteService.findPhotos(noteId))
                .created(note.getCreated())
                .tags(noteService.findTags(noteId))
                .build();
        return detail;
    }

    @PutMapping("/{note-id}")
    public void edit(@PathVariable(name = "note-id") Long noteId, @RequestBody PostNoteRequestDTO request,
                     HttpServletResponse response) throws NotFoundException {
        noteService.edit(noteId, request);
        response.setStatus(HttpStatus.OK.value());
    }

    @DeleteMapping("/{note-id}")
    public void delete(@PathVariable(name = "note-id") Long noteId,
                       HttpServletResponse response) {
        noteService.delete(noteId);
        response.setStatus(HttpStatus.OK.value());
    }
}
