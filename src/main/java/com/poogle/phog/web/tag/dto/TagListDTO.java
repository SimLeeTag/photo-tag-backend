package com.poogle.phog.web.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTagListResponseDTO {
    private Long tagId;
    private String tagName;
    private Integer frequency;
    private Boolean activated;
}
