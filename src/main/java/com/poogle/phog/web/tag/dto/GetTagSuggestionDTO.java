package com.poogle.phog.web.tag.dto;

import lombok.*;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTagSuggestionDTO {
    private List<String> suggestion;

}
