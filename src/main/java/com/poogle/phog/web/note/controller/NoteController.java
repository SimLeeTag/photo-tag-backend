package com.poogle.phog.web.note.controller;

import com.poogle.phog.service.NoteService;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
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
                       HttpServletResponse response) throws NotFound {
        noteService.save(request);
        response.setStatus(HttpStatus.CREATED.value());
    }
}
