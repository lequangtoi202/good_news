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
        return ResponseEntity.ok(tagService.addTag(tag));
    }

    @PutMapping(Routing.TAG_BY_ID)
    public ResponseEntity<?> updateTag(@PathVariable("tagId") final Long tagId, @RequestBody final Tag tag) {
        return ResponseEntity.ok(tagService.updateTag(tag, tagId));
    }

    @DeleteMapping(Routing.TAG_BY_ID)
    public ResponseEntity<?> deleteTag(@PathVariable("tagId") final Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(Routing.FOLLOW_TAG)
    public ResponseEntity<?> toggleFollowTag(@PathVariable("tagId") long tagId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        return ResponseEntity.ok(userTagService.followTag(tagId, currentUser.getId()));
    }

    @GetMapping(Routing.FOLLOW_TAG_STATUS)
    public ResponseEntity<?> getFollowStatus(@PathVariable("tagId") long tagId) throws Exception {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        return ResponseEntity.ok(userTagService.getFollowStatus(tagId, currentUser.getId()));
    }
}
