package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment commentReq, Long articleId, Long userId);

    Comment replyComment(Comment commentReq, Long articleId, Long parentId, Long userId);

    Comment updateComment(Comment commentReq, Long articleId, Long commentId, Long userId);

    void deleteComment(Long commentId, Long userId);

    List<Comment> getAllComments();

    List<Comment> getAllCommentsByArticleId(Long articleId);

    Comment getCommentById(Long commentId);

    List<Comment> getAllCommentsByParentId(Long parentId);
}
