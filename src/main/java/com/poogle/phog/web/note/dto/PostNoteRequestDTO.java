package com.poogle.phog.web.note.dto;

import com.poogle.phog.domain.Photo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class PostNoteRequestDTO {
    private String rawMemo;
    private List<Photo> photos;
    private Long userId;

    public PostNoteRequestDTO(String rawMemo, List<Photo> photos) {
        this.rawMemo = rawMemo;
        this.photos = photos;
    }

    public PostNoteRequestDTO() {
    }
}
