package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Comment;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.CategoryService;
import com.quangtoi.good_news.service.CommentService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping(Routing.COMMENTS)
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping(Routing.COMMENT_BY_ID)
    public ResponseEntity<?> getCommentById(@PathVariable("commentId") final Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @GetMapping(Routing.COMMENT_BY_PARENT_ID)
    public ResponseEntity<?> getCommentByParentId(@PathVariable("parentId") final Long parentId) {
        return ResponseEntity.ok(commentService.getAllCommentsByParentId(parentId));
    }

    @GetMapping(Routing.COMMENTS_BY_ARTICLE)
    public ResponseEntity<?> getAllCommentsByArticle(@PathVariable("articleId") final Long articleId) {
        return ResponseEntity.ok(commentService.getAllCommentsByArticleId(articleId));
    }

    @PostMapping(Routing.COMMENTS_BY_ARTICLE)
    public ResponseEntity<?> addComment(@PathVariable("articleId") final Long articleId, @RequestBody final Comment commentReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(commentService.addComment(commentReq, articleId, currentUser.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(Routing.COMMENTS_BY_ARTICLE_AND_PARENT_ID)
    public ResponseEntity<?> replyComment(@PathVariable("articleId") final Long articleId,
                                          @PathVariable("parentId") final Long parentId,
                                          @RequestBody final Comment commentReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(commentService.replyComment(commentReq, articleId, parentId, currentUser.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(Routing.COMMENT_BY_ARTICLE_AND_COMMENT_ID)
    public ResponseEntity<?> updateComment(@PathVariable("articleId") final Long articleId, @PathVariable("commentId") final Long commentId, @RequestBody Comment commentReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(commentService.updateComment(commentReq, articleId, commentId, currentUser.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(Routing.COMMENT_BY_ID)
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") final Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        commentService.deleteComment(commentId, currentUser.getId());
                        return ResponseEntity.ok("Successfully");
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
