package com.example.loginproject.service;

import com.example.loginproject.dto.BoardDeleteRequestDTO;
import com.example.loginproject.dto.BoardDetailResponseDTO;
import com.example.loginproject.mapper.BoardMapper;
import com.example.loginproject.model.Article;
import com.example.loginproject.model.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardMapper boardMapper;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<Article> getArticles(int page, int size) {
        int offset = (page - 1) * size;

        return boardMapper.getArticles(Paging.builder()
                        .offset(offset)
                        .size(size)
                        .build());
    }
    @Transactional
    public void saveArticle(String userId, String title, String content, MultipartFile file) {

        String path = null;

        if(file != null && !file.isEmpty()) {
            path = fileService.fileUpload(file);
        }

            boardMapper.saveArticle(
                Article.builder()
                        .title(title)
                        .content(content)
                        .userId(userId)
                        .filePath(path)
                        .build()
            );
    }


    public int getTotalArticleCnt() {
        return boardMapper.getArticleCnt();
    }

    @Transactional(readOnly = true)
    public Article getBoardDetail(long id) {
        return boardMapper.getArticleById(id);
    }

    public Resource downloadFile (String fileName) {
        return fileService.downloadFile(fileName);
    }

    public void updateArticle(long id, String title, String content, MultipartFile file, Boolean fileChanged,
                              String filePath) {

        String path = null;

        if(!file.isEmpty()) {
            path = fileService.fileUpload(file);
        }

        if(fileChanged) {
            fileService.deleteFile(filePath);
        }else {
            path = filePath;
        }

        boardMapper.updateArticle(
                Article.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .filePath(path)
                        .build()
        );

    }

    public void deleteArticle(long id, BoardDeleteRequestDTO requestDTO) {
        fileService.deleteFile(requestDTO.getFilePath());
        boardMapper.deleteArticle(id);
    }
}
