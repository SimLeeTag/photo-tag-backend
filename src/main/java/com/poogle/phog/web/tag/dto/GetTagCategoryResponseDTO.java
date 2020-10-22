package com.poogle.phog.web.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTagCategoryResponseDTO {
    private Long tagId;
    private String tagName;
    private Integer frequency;
    private Boolean activated;
    private String thumbnail;
}
