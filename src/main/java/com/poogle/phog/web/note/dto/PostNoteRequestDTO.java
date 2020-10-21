package com.poogle.phog.web.note.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNoteRequestDTO {
    private String rawMemoTag;
    private String rawMemo;
    private String created;
    private List<String> tags;
    private List<String> photos;
}
