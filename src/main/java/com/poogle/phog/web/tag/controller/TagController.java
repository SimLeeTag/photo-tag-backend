package com.poogle.phog.web.tag.controller;

import com.poogle.phog.service.TagService;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagCategoryResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagListResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagSuggestionDTO;
import com.poogle.phog.web.tag.dto.PatchTagRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RequestMapping("/tags")
@RestController
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/setting")
    public GetTagListResponseDTO list(@RequestAttribute("id") Long userId) {
        return tagService.tagResponseDTOList(userId);
    }

    @PatchMapping("/{tag-id}")
    public void activate(@PathVariable(name = "tag-id") Long tagId,
                         @RequestBody PatchTagRequestDTO request,
                         HttpServletResponse response) throws NotFound {
        tagService.changeActiveStatus(tagId, request);
        response.setStatus(HttpStatus.OK.value());
    }

    @GetMapping("/explore")
    public List<GetTagCategoryResponseDTO> categorize(
            @RequestAttribute("id") Long userId, final Pageable pageable) {
        return tagService.tagCategoryResponseDTOList(userId, pageable);
    }

    @GetMapping("")
    public List<GetNoteResponseDTO> taggedNotes(
            @RequestParam(value = "tag", required = true) List<Long> tags) throws NotFound {
        return tagService.getTaggedNoteList(tags);
    }

    @GetMapping(value = "/suggestion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GetTagSuggestionDTO suggest(@RequestPart("file") MultipartFile multipartFile) throws IOException, URISyntaxException {
        return tagService.suggestTags(multipartFile);
    }
}
