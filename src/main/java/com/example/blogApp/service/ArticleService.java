package com.example.blogApp.service;

import com.example.blogApp.dto.CommentDto;
import com.example.blogApp.exception.AppException;
import com.example.blogApp.model.Article;
import com.example.blogApp.model.Comment;
import com.example.blogApp.repository.ArticleRepository;
import com.example.blogApp.repository.CommentRepository;
import com.example.blogApp.util.ConvertArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    @Autowired
    ArticleService(ArticleRepository articleRepository){
            this.articleRepository = articleRepository;
    }
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ConvertArticleDto convertArticleDto;

    public Article saveArticle(Article article){
            return this.articleRepository.saveAndFlush(article);
    }

    public Article fetchArticle(Integer articleId){
        Optional<Article> article = this.articleRepository.findById(articleId);
        if(!article.isPresent())
            throw new AppException( "article not found");
        return article.get();
    }

    public Article updateArticle(Integer articleId, Article article){
        Optional<Article> oldArticleOptional = this.articleRepository.findById(articleId);
        if(!oldArticleOptional.isPresent())
            throw new AppException("blog not present");
        Article oldArticle = oldArticleOptional.get();
//        if(oldArticle == null)
//            throw new RuntimeException( "article not found");
         convertArticleDto.convertArticle(article, oldArticle);
         return saveArticle(oldArticle);
    }

    public void deleteArticle(Integer articleId) {
         this.articleRepository.deleteById(articleId);
    }

    public List<Article> fetchAllArticleOfBlog(Integer blogId) {
        Optional<List<Article>> articleList = this.articleRepository.findByBlogId(blogId);
        if(!articleList.isPresent())
            throw new AppException("article not found for the given blog id: "+ blogId);
        return articleList.get();
//        this.articleRepository.getArticleByBlogId(blogId);
    }


}
