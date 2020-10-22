package com.poogle.phog.web.photo.dto;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPhotoResponseDTO {
    private Long photoId;
    private String url;
}
