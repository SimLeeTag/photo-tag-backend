package com.poogle.phog.service;

import com.poogle.phog.domain.NoteTagRepository;
import com.poogle.phog.domain.Tag;
import com.poogle.phog.domain.TagRepository;
import com.poogle.phog.web.tag.dto.GetTagListResponseDTO;
import com.poogle.phog.web.tag.dto.TagListDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;
    private NoteTagRepository noteTagRepository;

    public TagService(TagRepository tagRepository, NoteTagRepository noteTagRepository) {
        this.tagRepository = tagRepository;
        this.noteTagRepository = noteTagRepository;
    }

    public GetTagListResponseDTO tagResponseDTOList(Long userId) {
        List<TagListDTO> getActivatedTags = new ArrayList<>();
        List<TagListDTO> getArchivedTags = new ArrayList<>();
        for (Tag tag : tagRepository.findTagsByUserIdAndActivatedTrueOrderByTagNameAsc(userId)) {
            TagListDTO getTagResponseDTO = TagListDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .build();
            getActivatedTags.add(getTagResponseDTO);
        }
        for (Tag tag : tagRepository.findTagsByUserIdAndActivatedFalseOrderByTagNameAsc(userId)) {
            TagListDTO getTagResponseDTO = TagListDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .build();
            getArchivedTags.add(getTagResponseDTO);
        }
        return GetTagListResponseDTO.builder()
                .activatedList(getActivatedTags)
                .archivedList(getArchivedTags)
                .build();
    }
}
