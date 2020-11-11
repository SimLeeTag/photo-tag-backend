package com.poogle.phog.web.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GetTagListResponseDTO {

    private List<TagListDTO> activatedList;
    private List<TagListDTO> archivedList;

    @Builder
    public GetTagListResponseDTO(List<TagListDTO> activatedList, List<TagListDTO> archivedList) {
        this.activatedList = activatedList;
        this.archivedList = archivedList;
    }
}
