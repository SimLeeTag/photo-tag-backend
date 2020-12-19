package com.poogle.phog.service;

import com.poogle.phog.domain.*;
import com.poogle.phog.web.tag.dto.GetTagCategoryResponseDTO;
import com.poogle.phog.web.tag.dto.GetTagListResponseDTO;
import com.poogle.phog.web.tag.dto.PatchTagRequestDTO;
import com.poogle.phog.web.tag.dto.TagListDTO;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TagService {

    private TagRepository tagRepository;
    private NoteTagRepository noteTagRepository;
    private NoteRepository noteRepository;

    public TagService(TagRepository tagRepository, NoteTagRepository noteTagRepository, NoteRepository noteRepository) {
        this.tagRepository = tagRepository;
        this.noteTagRepository = noteTagRepository;
        this.noteRepository = noteRepository;
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

    public void changeActiveStatus(Long tagId, PatchTagRequestDTO request) throws NotFound {
        Tag tag = tagRepository.findById(tagId).orElseThrow(NotFound::new);
        tag.setActivated(request.getActivated());
        tagRepository.save(tag);
    }

    public List<GetTagCategoryResponseDTO> tagCategoryResponseDTOList(Long userId, Pageable pageable) {
        List<GetTagCategoryResponseDTO> getCategorizedTags = new ArrayList<>();
        Page<Tag> tags = tagRepository.findAllByUserIdAndActivatedTrue(userId, pageable);
        for (Tag tag : tags) {
            List<Photo> photoList = noteRepository.findPhotoByNoteId(noteTagRepository.findRecentNoteIdByTagId(tag.getTagId()));
            GetTagCategoryResponseDTO getTagCategoryResponseDTO = GetTagCategoryResponseDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .thumbnail(photoList.get(0).getUrl())
                    .build();
            getCategorizedTags.add(getTagCategoryResponseDTO);
        }
        return getCategorizedTags;
    }
}
