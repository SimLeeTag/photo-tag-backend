package com.poogle.phog.web.note.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteResponseDTO {
    private Long noteId;
    private List<String> tags;
    private LocalDateTime created;
    private String rawMemo;
    private List<String> photos;
}
