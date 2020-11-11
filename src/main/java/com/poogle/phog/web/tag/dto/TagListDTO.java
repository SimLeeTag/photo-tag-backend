package com.poogle.phog.web.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TagListDTO {
    private Long tagId;
    private String tagName;
    private Integer frequency;
    private Boolean activated;
}
