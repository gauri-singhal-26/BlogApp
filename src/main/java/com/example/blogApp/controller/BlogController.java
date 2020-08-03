package com.example.blogApp.controller;

import com.example.blogApp.model.Article;
import com.example.blogApp.model.Blog;
import com.example.blogApp.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping(value = "v1/create")
    public ResponseEntity<Blog> createNewBlog(@RequestBody Blog blog) throws Exception {
        return new ResponseEntity<Blog>(blogService.saveBlog(blog), HttpStatus.OK);
    }

    @GetMapping(value = "v1/get/{id}")
    public ResponseEntity<Blog> fetchBlog(@PathVariable Integer id) {
        return new ResponseEntity<Blog>(blogService.fetchBlog(id), HttpStatus.OK);
    }

    @PutMapping(value = "v1/update/{id}")
    public ResponseEntity<Blog> updateArticle(@PathVariable Integer id, @RequestBody Blog blog) {
        return new ResponseEntity<Blog>(blogService.updateBlog(id, blog), HttpStatus.OK);
    }


    @DeleteMapping(value = "v1/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
    }
}
