package com.example.blogApp.service;

import com.example.blogApp.dto.CommentDto;
import com.example.blogApp.model.Comment;
import com.example.blogApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

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
