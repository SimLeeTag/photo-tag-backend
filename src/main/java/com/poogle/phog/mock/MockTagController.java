package com.poogle.phog.mock;

import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.photo.dto.GetPhotoResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagCategoryResponseDTO;
import com.poogle.phog.web.tag.dto.TagListDTO;
import com.poogle.phog.web.tag.dto.GetTagSuggestionDTO;
import com.poogle.phog.web.tag.dto.PatchTagRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/mock/tags")
@RestController
public class MockTagController {

    @GetMapping("/explore")
    public List<GetTagCategoryResponseDTO> tagCategoryList(
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset) {
        List<GetTagCategoryResponseDTO> getTagCategoryResponseDTOList = new ArrayList<>();
        List<GetPhotoResponseDTO> photos = new ArrayList<>();
        photos.add(GetPhotoResponseDTO.builder()
                .url("sdfsdsdfsdfsdfsdfsdfsdfsdgdfgfsdf")
                .build());
        photos.add(GetPhotoResponseDTO.builder()
                .url("asdafghfghfghfhgwerwerwerwerwsdf")
                .build());
        photos.add(GetPhotoResponseDTO.builder()
                .url("werrtyytrywerwerwerwerwerwerwqweqfdgfghjkdfvghjrt")
                .build());
        getTagCategoryResponseDTOList.add(GetTagCategoryResponseDTO.builder()
                .tagId(3L)
                .tagName("#ootd")
                .frequency(4)
                .activated(true)
                .thumbnail(photos.get(1).getUrl())
                .build());
        getTagCategoryResponseDTOList.add(GetTagCategoryResponseDTO.builder()
                .tagId(2L)
                .tagName("#shopping")
                .frequency(6)
                .activated(true)
                .thumbnail(photos.get(1).getUrl())
                .build());
        getTagCategoryResponseDTOList.add(GetTagCategoryResponseDTO.builder()
                .tagId(5L)
                .tagName("#달리기")
                .frequency(6)
                .activated(true)
                .thumbnail(photos.get(1).getUrl())
                .build());
        return getTagCategoryResponseDTOList;
    }

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
