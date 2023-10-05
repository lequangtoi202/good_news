package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Tag;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.TagService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.service.UserTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class TagController {
    private final TagService tagService;
    private final UserService userService;
    private final UserTagService userTagService;


    @GetMapping("/api/v1/tags")
    public ResponseEntity<?> getAllTags(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        if (pageNumber != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return ResponseEntity.ok(tagService.getAllTagsPageable(pageable));
        }
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/api/v1/articles/{articleId}/tags")
    public ResponseEntity<?> getAllTagsOfArticle(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(tagService.getAllTagsOfArticle(articleId));
    }

    @GetMapping("/api/v1/tags/{tagId}")
    public ResponseEntity<?> getTagById(@PathVariable("tagId") final Long tagId) {
        return ResponseEntity.ok(tagService.getTagById(tagId));
    }

    @PostMapping("/api/v1/tags")
    public ResponseEntity<?> addTag(@RequestBody final Tag tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(tagService.addTag(tag));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/tags/{tagId}")
    public ResponseEntity<?> updateTag(@PathVariable("tagId") final Long tagId, @RequestBody final Tag tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(tagService.updateTag(tag, tagId));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/tags/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable("tagId") final Long tagId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    tagService.deleteTag(tagId);
                    return ResponseEntity.ok("Successfully");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/v1/tags/{tagId}/follow")
    public ResponseEntity<?> toggleFollowTag(@PathVariable("tagId") long tagId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(userTagService.followTag(tagId, currentUser.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/tags/{tagId}/follow-status")
    public ResponseEntity<?> getFollowStatus(@PathVariable("tagId") long tagId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(userTagService.getFollowStatus(tagId, currentUser.getId()));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
