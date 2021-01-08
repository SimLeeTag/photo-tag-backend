package com.poogle.phog.web.note.controller;

import com.poogle.phog.domain.Note;
import com.poogle.phog.domain.Photo;
import com.poogle.phog.service.NoteService;
import com.poogle.phog.service.S3Service;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/notes")
@RestController
public class NoteController {

    private NoteService noteService;
    private S3Service s3Service;

    public NoteController(NoteService noteService, S3Service s3Service) {
        this.noteService = noteService;
        this.s3Service = s3Service;
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(@RequestPart("file") List<MultipartFile> multipartFiles,
                       @RequestPart("request") PostNoteRequestDTO request,
                       @RequestAttribute("id") Long userId,
                       HttpServletResponse response) throws IOException {

        List<Photo> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String url = s3Service.upload(multipartFile, "public");
            Photo photo = Photo.builder()
                    .url(url)
                    .build();
            urls.add(photo);
        }

        request.setPhotos(urls);
        noteService.save(request, userId);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @GetMapping("/{note-id}")
    public GetNoteResponseDTO detail(@PathVariable(name = "note-id") Long noteId,
                                     @RequestAttribute("id") Long userId) throws NotFound {
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
                     @RequestAttribute("id") Long userId, HttpServletResponse response) throws NotFoundException {
        noteService.edit(userId, noteId, request);
        response.setStatus(HttpStatus.OK.value());
    }

    @DeleteMapping("/{note-id}")
    public void delete(@PathVariable(name = "note-id") Long noteId,
                       HttpServletResponse response) {
        noteService.delete(noteId);
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public List<GetNoteResponseDTO> search(@RequestAttribute("id") Long userId,
                                           @RequestParam(value = "word", required = true) String word) throws NotFound {
        return noteService.findAllNotes(userId, word);
    }
}
