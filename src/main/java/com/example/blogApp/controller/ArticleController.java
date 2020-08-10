package com.example.blogApp.controller;

import com.example.blogApp.dto.CommentDto;
import com.example.blogApp.model.Article;
import com.example.blogApp.model.Comment;
import com.example.blogApp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping(value = "v1/create")
    public ResponseEntity<Article> createNewArticle(@RequestBody Article article) throws Exception {
        return new ResponseEntity<Article>(articleService.saveArticle(article), HttpStatus.OK);
    }

    @GetMapping(value = "v1/get/{id}")
    public ResponseEntity<Article> fetchArticle(@PathVariable Integer id) {
        return new ResponseEntity<Article>(articleService.fetchArticle(id), HttpStatus.OK);
    }

    @PutMapping(value = "v1/update/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Integer id, @RequestBody Article article) {
        return new ResponseEntity<Article>(articleService.updateArticle(id, article), HttpStatus.OK);
    }

    @DeleteMapping(value = "v1/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "v1/fetchAllArticle/{id}")
    public ResponseEntity<List<Article>> getArticlesUsingBlogId(@PathVariable Integer id) {
        return new  ResponseEntity<List<Article>>(articleService.fetchAllArticleOfBlog(id), HttpStatus.OK);
    }

}
