package com.example.loginproject.model;

import com.example.loginproject.dto.BoardDetailResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class Article {

    private Long id;
    private String title;
    private String content;
    private String userId;
    private String filePath;
    private LocalDateTime created;
    private LocalDateTime updated;

    public BoardDetailResponseDTO toBoardDetailDTO() {
        return BoardDetailResponseDTO.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .filePath(filePath)
                .created(created)
                .build();
    }
}
