package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Bookmark;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.BookmarkService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final UserService userService;

    @GetMapping(Routing.BOOKMARK_OF_USER)
    public ResponseEntity<?> getAllBookmarksOfUser(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(bookmarkService.getAllBookmarksOfUser(userId));
    }

    @GetMapping(Routing.BOOKMARKS)
    public ResponseEntity<?> getAllBookmarks(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNumber") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(bookmarkService.getAllBookmarks(pageable));
    }

    @GetMapping(Routing.BOOKMARK_OF_ME)
    public ResponseEntity<?> getAllBookmarksOfMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        return ResponseEntity.ok(bookmarkService.getAllBookmarksOfUser(currentUser.getId()));
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(Routing.BOOKMARKS)
    public ResponseEntity<?> addArticleToBookmark(@RequestParam("articleId") final Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        Bookmark bookmark = bookmarkService.addArticleToBookmark(articleId, currentUser);
                        if (bookmark != null) {
                            return ResponseEntity.ok(bookmark);
                        } else {
                            return ResponseEntity.noContent().build();
                        }
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(Routing.BOOKMARKS)
    public ResponseEntity<?> deleteArticleFromBookmark(@RequestParam("articleId") final Long articleId) {
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

    @DeleteMapping(Routing.BOOKMARK_BY_ID)
    public ResponseEntity<?> deleteBookmark(@PathVariable Long bookmarkId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        bookmarkService.deleteBookmark(bookmarkId, currentUser);
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
