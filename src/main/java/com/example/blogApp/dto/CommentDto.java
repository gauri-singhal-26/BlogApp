package com.example.blogApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ArticleId",
        "Reply",
        "Comments",
        "CommentId"
})
public class CommentDto {
    @JsonProperty("ArticleId")
    private Integer articleId;
    @JsonProperty("Reply")
    private String reply;

    @JsonProperty("Comments")
//    @OneToMany(mappedBy="comment", fetch= FetchType.EAGER)
    private List<CommentDto> comments;

    @JsonProperty("CommentId")
    private Integer commentId;

    @JsonProperty("ArticleId")
    public Integer getArticleId() {
        return articleId;
    }

    @JsonProperty("ArticleId")
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @JsonProperty("Reply")
    public String getReply() {
        return reply;
    }

    @JsonProperty("Reply")
    public void setReply(String reply) {
        this.reply = reply;
    }

    @JsonProperty("Comments")
    public List<CommentDto> getComments() {
        return comments;
    }

    @JsonProperty("Comments")
    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @JsonProperty("CommentId")
    public Integer getCommentId() {
        return commentId;
    }

    @JsonProperty("CommentId")
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public CommentDto(Integer articleId, String reply, List<CommentDto> comments, Integer commentId) {
        this.articleId = articleId;
        this.reply = reply;
        this.comments = comments;
        this.commentId = commentId;
    }

    public CommentDto() {
    }

    public CommentDto(Integer articleId, String reply, Integer commentId) {
        this.articleId = articleId;
        this.reply = reply;
        this.commentId = commentId;
    }
}
