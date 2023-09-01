package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.BookmarkService;
import com.quangtoi.good_news.service.UserService;
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
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final UserService userService;

    @GetMapping("/api/v1/users/{userId}/bookmarks")
    public ResponseEntity<?> getAllBookmarksOfUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bookmarkService.getAllBookmarksOfUser(userId));
    }

    @PostMapping("/api/v1/bookmarks")
    public ResponseEntity<?> addArticleToBookmark(@RequestParam("articleId") Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        return ResponseEntity.ok(bookmarkService.addArticleToBookmark(articleId, currentUser));
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/bookmarks")
    public ResponseEntity<?> deleteArticleFromBookmark(@RequestParam("articleId") Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        bookmarkService.deleteArticleFromBookmark(articleId, currentUser);
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
