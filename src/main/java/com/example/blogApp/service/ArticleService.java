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
            throw new AppException("article not founf for the given blog id: "+ blogId);
        return articleList.get();
//        this.articleRepository.getArticleByBlogId(blogId);
    }
    public String addComment(Comment comment) {
        this.commentRepository.save(comment);
        return "comment added";
    }


    private CommentDto createNormalNode(Integer articleId, String reply, Integer commentId, CommentDto nestedChildResponse) {

        CommentDto response = new CommentDto(articleId, reply, commentId);
        List<CommentDto> nestedChildList = new ArrayList<>();
        nestedChildList.add(nestedChildResponse);

        response.setComments(nestedChildList);
        return response;


    }
    public List<CommentDto> getComment() {
        List<Comment> comments = this.commentRepository.findLevelOneComments();
        Map<Integer, Comment> mp = new HashMap<>();
        for(int i=0;i<comments.size();i++) {
            mp.put(comments.get(i).getId(), comments.get(i));
        }
        List<Comment> threaded = new ArrayList<Comment>();
        List<Comment> removeComments = new ArrayList<Comment>();
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            if(c.getParentCommentId() == null){
                c.setCommentDepth(0);
                c.setChildCount(0);
                threaded.add(c);
                removeComments.add(c);
            }
        }

        if(removeComments.size() > 0){
            comments.removeAll(removeComments);
            removeComments.clear();
        }
        int depth = 0;
        while(comments.size() > 0 && depth <= 2){
            depth++;
            for(int j = 0; j< comments.size(); j++){
                Comment child = comments.get(j);
                for(int i = 0; i < threaded.size(); i++){
                    Comment parent = threaded.get(i);
                    if(parent.getId() == child.getParentCommentId()){
                        parent.setChildCount(parent.getChildCount()+1);
                        child.setCommentDepth(depth+parent.getCommentDepth());
                        child.setChildCount(0);
                        threaded.add(i+parent.getChildCount(),child);
                        removeComments.add(child);
                        continue;
                    }
                }
            }
            if(removeComments.size() > 0){
                comments.removeAll(removeComments);
                removeComments.clear();
            }
        }
        List<CommentDto> dto = new ArrayList<>();
        List<Comment> copycomment = this.commentRepository.findLevelOneComments();
        for(int i=0;i<threaded.size();i++) {
            if(dto.size()>=10) break;
            if(threaded.get(i).getParentCommentId()!=null && threaded.get(i).getCommentDepth() == 1) {
                for(int j=0;j<copycomment.size();j++) {
                    if(copycomment.get(j).getId() == threaded.get(i).getParentCommentId()) {
                        CommentDto nestedResponse = new CommentDto(copycomment.get(j).getArticleId(), copycomment.get(j).getComment(), copycomment.get(j).getId());
                        dto.add(createNormalNode(threaded.get(i).getArticleId(), threaded.get(i).getComment(), threaded.get(i).getId(), nestedResponse));
                    }

                }
                continue;
            }
            List<CommentDto> emptyList = new ArrayList<>();
            CommentDto parent = new CommentDto(threaded.get(i).getArticleId(),threaded.get(i).getComment(),emptyList, threaded.get(i).getId() );
            dto.add(parent);
        }
        return dto;

    }

}
