package com.poogle.phog.mock;

import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.photo.dto.GetPhotoResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagSuggestionDTO;
import com.poogle.phog.web.tag.dto.TagListDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/mock/tags")
@RestController
public class MockTagController {
    
    @GetMapping("")
    public List<GetNoteResponseDTO> taggedNotes(
            @RequestParam(value = "tag", defaultValue = "1") int tagId) {
        List<GetNoteResponseDTO> notes = new ArrayList<>();
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

        List<TagListDTO> tags = new ArrayList<>();
        tags.add(TagListDTO.builder()
                .tagName("#달리기")
                .build());
        tags.add(TagListDTO.builder()
                .tagName("#ootd")
                .build());
        tags.add(TagListDTO.builder()
                .tagName("#shopping")
                .build());
        List<String> tagList = new ArrayList<>();
        for (TagListDTO tag : tags) {
            tagList.add(tag.getTagName());
        }
        notes.add(GetNoteResponseDTO.builder()
                .noteId(1L)
                .photos(photoList)
                .tags(tagList)
                .build());
        notes.add(GetNoteResponseDTO.builder()
                .noteId(2L)
                .photos(photoList)
                .tags(tagList)
                .build());
        notes.add(GetNoteResponseDTO.builder()
                .noteId(3L)
                .photos(photoList)
                .tags(tagList)
                .build());
        notes.add(GetNoteResponseDTO.builder()
                .noteId(4L)
                .photos(photoList)
                .tags(tagList)
                .build());
        return notes;
    }

    @GetMapping("/suggestion")
    public GetTagSuggestionDTO suggest(
            @RequestParam(value = "photo", defaultValue = "sdfsdfffff") String photo) {
        List<String> recommendList = new ArrayList<>();
        recommendList.add("clothes");
        recommendList.add("sport");
        recommendList.add("마라톤");
        new GetTagSuggestionDTO();
        return GetTagSuggestionDTO.builder()
                .suggestion(recommendList)
                .build();
    }
}
