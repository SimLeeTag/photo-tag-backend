package com.poogle.phog.web.tag.controller;

import com.poogle.phog.service.TagService;
import com.poogle.phog.web.tag.dto.GetTagListResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
