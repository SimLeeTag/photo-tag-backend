package com.poogle.phog.web.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GetTagCategoryResponseDTO {
    private Long tagId;
    private String tagName;
    private Integer frequency;
    private Boolean activated;
    private String thumbnail;

    @Builder
    public GetTagCategoryResponseDTO(Long tagId, String tagName, Integer frequency, Boolean activated, String thumbnail) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.frequency = frequency;
        this.activated = activated;
        this.thumbnail = thumbnail;
    }
}
