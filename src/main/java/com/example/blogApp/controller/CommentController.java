package com.example.blogApp.controller;

import com.example.blogApp.dto.CommentDto;
import com.example.blogApp.model.Comment;
import com.example.blogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "v1/addComment")
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        return new ResponseEntity<String>(commentService.addComment(comment), HttpStatus.OK);
    }
    @GetMapping(value = "v1/getComment")
    public ResponseEntity<List<CommentDto>> getComments() {
        return new  ResponseEntity<List<CommentDto>>(commentService.getComment(), HttpStatus.OK);
    }
}
