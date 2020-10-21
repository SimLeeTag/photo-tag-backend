package com.poogle.phog.mock;

import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.note.dto.PostNoteRequestDTO;
import com.poogle.phog.web.photo.dto.GetPhotoResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagListResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/mock/notes")
@RestController
public class MockNoteController {
    private static final Logger log = LoggerFactory.getLogger(MockNoteController.class);

    @PostMapping()
    public void create(@RequestBody PostNoteRequestDTO request, HttpServletResponse response) {
        log.debug("[*] create - request: {}", request);
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("/{noteId}")
    public GetNoteResponseDTO detail(@PathVariable(name = "noteId") long noteId) {
        new GetNoteResponseDTO();
        GetNoteResponseDTO note = GetNoteResponseDTO.builder()
                .noteId(1L)
                .rawMemoTag("Let's run with Nike. #달리기 힘들다. #ootd #shopping")
                .created("2020-10-19 14:24:35")
                .build();

        List<GetPhotoResponseDTO> photos = new ArrayList<>();
        photos.add(GetPhotoResponseDTO.builder()
                .url("sdfsdfsdf")
                .build());
        photos.add(GetPhotoResponseDTO.builder()
                .url("asdasdf")
                .build());
        photos.add(GetPhotoResponseDTO.builder()
                .url("werrtyytryrt")
                .build());
        List<String> photoList = new ArrayList<>();
        for (GetPhotoResponseDTO photo : photos) {
            photoList.add(photo.getUrl());
        }
        note.setPhotos(photoList);

        List<GetTagListResponseDTO> tags = new ArrayList<>();
        tags.add(GetTagListResponseDTO.builder()
                .tagName("#달리기")
                .build());
        tags.add(GetTagListResponseDTO.builder()
                .tagName("#ootd")
                .build());
        tags.add(GetTagListResponseDTO.builder()
                .tagName("#shopping")
                .build());
        List<String> tagList = new ArrayList<>();
        for (GetTagListResponseDTO tag : tags) {
            tagList.add(tag.getTagName());
        }
        note.setTags(tagList);
        return note;
    }

    @PutMapping("/{noteId}")
    public void edit(@PathVariable(name = "noteId") long noteId,
                     @RequestBody PostNoteRequestDTO request, HttpServletResponse response) {
        log.debug("[*] put - noteId : {}, request : {}", noteId, request);
        response.setStatus(HttpStatus.OK.value());
    }

    @DeleteMapping("/{noteId}")
    public void delete(@PathVariable(name = "noteId") long noteId, HttpServletResponse response) {
        log.debug("[*] delete - noteId: {}", noteId);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
