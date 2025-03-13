package com.example.loginproject.dto;

import com.example.loginproject.model.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDTO {

    List<Article> articles;
    boolean last;
}
