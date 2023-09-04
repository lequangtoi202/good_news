package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Tag;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.TagService;
import com.quangtoi.good_news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final UserService userService;


    @GetMapping("/api/v1/tags")
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
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
}
