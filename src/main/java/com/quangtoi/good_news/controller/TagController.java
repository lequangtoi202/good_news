package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Tag;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.TagService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.service.UserTagService;
import com.quangtoi.good_news.utils.Routing;
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


    @GetMapping(Routing.TAGS)
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

    @GetMapping(Routing.TAGS_OF_ARTICLE)
    public ResponseEntity<?> getAllTagsOfArticle(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(tagService.getAllTagsOfArticle(articleId));
    }

    @GetMapping(Routing.TAG_BY_ID)
    public ResponseEntity<?> getTagById(@PathVariable("tagId") final Long tagId) {
        return ResponseEntity.ok(tagService.getTagById(tagId));
    }

    @PostMapping(Routing.TAGS)
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

    @PutMapping(Routing.TAG_BY_ID)
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

    @DeleteMapping(Routing.TAG_BY_ID)
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

    @PostMapping(Routing.FOLLOW_TAG)
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

    @GetMapping(Routing.FOLLOW_TAG_STATUS)
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
