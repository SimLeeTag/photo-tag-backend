package com.poogle.phog.web.note.controller;

import com.poogle.phog.domain.Note;
import com.poogle.phog.domain.Photo;
import com.poogle.phog.exception.AuthorizationException;
import com.poogle.phog.exception.NotFoundException;
import com.poogle.phog.exception.VerificationException;
import com.poogle.phog.service.NoteService;
import com.poogle.phog.service.S3Service;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import lombok.extern.slf4j.Slf4j;
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
                       HttpServletResponse response) throws IOException, VerificationException {
        log.debug("photos:{}", multipartFiles);

        List<Photo> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                String url = s3Service.upload(multipartFile, "public");
                Photo photo = Photo.builder()
                        .url(url)
                        .build();
                urls.add(photo);
            } else {
                throw new VerificationException("Photos can't be blank");
            }
        }

        request.setPhotos(urls);
        if (!request.getRawMemo().isEmpty()) {
            noteService.save(request, userId);
        } else {
            throw new VerificationException("Note can't be blank");
        }
        response.setStatus(HttpStatus.CREATED.value());
    }

    @GetMapping("/{note-id}")
    public GetNoteResponseDTO detail(@PathVariable(name = "note-id") Long noteId,
                                     @RequestAttribute("id") Long userId) throws NotFoundException {
        Note note = noteService.findNote(noteId);
        if (note == null) {
            throw NotFoundException.noteNotFound();
        }
        if (!userId.equals(note.getUser().getId())) {
            throw AuthorizationException.accessWrong();
        } else {
            return GetNoteResponseDTO.builder()
                    .noteId(noteId)
                    .rawMemo(note.getRawMemo())
                    .photos(noteService.findPhotos(noteId))
                    .created(note.getCreated())
                    .tags(noteService.findTags(noteId))
                    .build();
        }
    }

    @PutMapping("/{note-id}")
    public void edit(@PathVariable(name = "note-id") Long noteId, @RequestBody PostNoteRequestDTO request,
                     @RequestAttribute("id") Long userId, HttpServletResponse response) throws NotFoundException {
        noteService.edit(userId, noteId, request);
        response.setStatus(HttpStatus.OK.value());
    }

    @DeleteMapping("/{note-id}")
    public void delete(@PathVariable(name = "note-id") Long noteId,
                       @RequestAttribute("id") Long userId, HttpServletResponse response) {
        noteService.delete(noteId, userId);
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public List<GetNoteResponseDTO> search(@RequestAttribute("id") Long userId,
                                           @RequestParam(value = "word") String word) throws NotFoundException {
        return noteService.findAllNotes(userId, word);
    }
}
