package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Comment;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.repository.ArticleRepository;
import com.quangtoi.good_news.repository.CommentRepository;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Comment addComment(Comment commentReq, Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Comment comment = Comment.builder()
                .article(article)
                .content(commentReq.getContent())
                .active(true)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .parentId(commentReq.getParentId() == null ? null : commentReq.getParentId())
                .userId(userId)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment replyComment(Comment commentReq, Long articleId, Long parentId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", parentId));
        Comment comment = Comment.builder()
                .article(article)
                .content(commentReq.getContent())
                .active(true)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .parentId(parentComment)
                .userId(userId)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment commentReq, Long articleId, Long commentId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        List<Role> roles = roleRepository.getAllByUser(userId);
        boolean hasRoleAdmin = roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        if (!hasRoleAdmin) {
            throw new BadCredentialsException("You do not have role to update this comment");
        }
        comment.setArticle(article);
        comment.setContent(commentReq.getContent());
        comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        List<Role> roles = roleRepository.getAllByUser(userId);
        boolean hasRoleAdmin = roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        if (!hasRoleAdmin) {
            throw new BadCredentialsException("You do not have role to update this comment");
        }
        comment.setActive(false);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAllByActive(true);
    }

    @Override
    public List<Comment> getAllCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        return commentRepository.findAllByArticleAndActive(article, true);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    @Override
    public List<Comment> getAllCommentsByParentId(Long parentId) {
        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", parentId));
        return commentRepository.findAllByParentIdAndActive(parent, true);
    }
}
