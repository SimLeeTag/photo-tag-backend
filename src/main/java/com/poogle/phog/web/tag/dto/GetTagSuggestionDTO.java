package com.poogle.phog.web.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
public class GetTagSuggestionDTO {
    private List<String> tagsEn;
    private List<String> tagsKr;

    @Builder
    public GetTagSuggestionDTO(List<String> tagsEn, List<String> tagsKr) {
        this.tagsEn = tagsEn;
        this.tagsKr = tagsKr;
    }
}
