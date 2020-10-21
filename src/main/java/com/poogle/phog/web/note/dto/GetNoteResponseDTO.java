package com.poogle.phog.web.note.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteResponseDTO {
    private Long noteId;
    private List<String> tags;
    private String created;
    private String rawMemoTag;
    private List<String> photos;
}
