package com.example.loginproject.mapper;

import com.example.loginproject.dto.BoardDetailResponseDTO;
import com.example.loginproject.model.Article;
import com.example.loginproject.model.Paging;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface BoardMapper {

    void saveArticle(Article article);
    List<Article>getArticles(Paging page);
    int getArticleCnt();
    Article getArticleById(long id);
    void updateArticle(Article article);
    void deleteArticle(long id);
}
