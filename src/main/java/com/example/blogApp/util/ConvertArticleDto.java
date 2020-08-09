package com.example.blogApp.util;


import com.example.blogApp.model.Article;
import com.example.blogApp.model.Blog;
import org.springframework.stereotype.Component;


@Component
public class ConvertArticleDto {
    public Article convertArticle(Article source, Article destination) {
        String title = source.getTitle() != null ? source.getTitle() : destination.getTitle();
        Integer blogId = source.getBlogId() != null ? source.getBlogId() : destination.getBlogId();
        Integer userId = source.getUserId() != null ? source.getUserId() : destination.getUserId();
        String content = source.getContent()!=null ? source.getContent() : destination.getContent();
        destination.setBlogId(blogId);
        destination.setContent(content);
        destination.setTitle(title);
        destination.setUserId(userId);
        return destination;
    }
    public Blog convertBlog(Blog source, Blog destination) {
        String name = source.getName() != null ? source.getName() : destination.getName();
        String url = source.getUrl() != null ? source.getUrl() : destination.getUrl();
        Integer userId = source.getUserId() != null ? source.getUserId() : destination.getUserId();
        destination.setName(name);
        destination.setUrl(url);
        destination.setUserId(userId);
        return destination;
    }
}
