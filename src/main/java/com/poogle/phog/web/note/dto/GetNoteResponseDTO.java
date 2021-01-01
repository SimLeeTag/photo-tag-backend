package com.poogle.phog.web.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteResponseDTO implements Comparable<GetNoteResponseDTO> {
    private Long noteId;
    private List<String> tags;
    private LocalDateTime created;
    private String rawMemo;
    private List<String> photos;

    @Override
    public int compareTo(GetNoteResponseDTO getNoteResponseDTO) {
        if (this.getCreated().isAfter(getNoteResponseDTO.getCreated())) {
            return -1;
        } else if (this.getCreated() == getNoteResponseDTO.getCreated()) {
            return 0;
        } else {
            return 1;
        }
    }
}
