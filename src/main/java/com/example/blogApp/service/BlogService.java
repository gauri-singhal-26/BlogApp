package com.example.blogApp.service;

import com.example.blogApp.exception.AppException;
import com.example.blogApp.model.Article;
import com.example.blogApp.model.Blog;
import com.example.blogApp.repository.BlogRepository;
import com.example.blogApp.util.ConvertArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {
    private BlogRepository blogRepository;
    @Autowired
    ConvertArticleDto convertArticleDto;
    @Autowired
    BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }
    public Blog saveBlog(Blog blog) {
        return this.blogRepository.saveAndFlush(blog);
    }

    public Blog fetchBlog(Integer blogId){
        Optional<Blog> blog = this.blogRepository.findById(blogId);
        if(!blog.isPresent())
            throw new AppException( "Blog not found");
        return blog.get();
    }
    public Blog updateBlog(Integer blogId, Blog blog){
        Optional<Blog> oldBlogOptional = this.blogRepository.findById(blogId);
        if(!oldBlogOptional.isPresent())
            throw new AppException("blog not present");
        Blog olgBlog = oldBlogOptional.get();
//        if(olgBlog == null)
//            throw new RuntimeException( "Blog not found");
        convertArticleDto.convertBlog(blog, olgBlog);
        return saveBlog(olgBlog);
    }
    public void deleteBlog(Integer blogId) {
        this.blogRepository.deleteById(blogId);
    }
}
