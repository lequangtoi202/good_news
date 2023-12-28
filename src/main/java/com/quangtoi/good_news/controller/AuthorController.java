package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.dto.AuthorResponse;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.AuthorRequest;
import com.quangtoi.good_news.service.AuthorService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthorController {
    private final AuthorService authorService;
    private final UserService userService;

    @PostMapping(Routing.AUTHORS)
    public ResponseEntity<?> registerAuthor(@RequestBody final AuthorRequest authorRequest) throws Exception {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        try {
            AuthorResponse authorResponse = authorService.registerAuthor(currentUser, authorRequest);
            return ResponseEntity.ok(authorResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(Routing.AUTHORS)
    public ResponseEntity<?> updateAuthor(@RequestBody final AuthorRequest authorRequest) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        AuthorResponse authorResponse = authorService.updateAuthor(currentUser, authorRequest);
        return ResponseEntity.ok(authorResponse);
    }

    @PutMapping(Routing.AUTHOR_BY_ID)
    public ResponseEntity<?> updateAuthorById(@RequestBody final AuthorRequest authorRequest, @PathVariable("authorId") final Long authorId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        Authors authorUpdated = authorService.updateAuthorById(currentUser, authorRequest, authorId);
        return ResponseEntity.ok(authorUpdated);
    }

    @PostMapping(Routing.CONFIRM_AUTHOR)
    public ResponseEntity<?> confirmAuthor(@PathVariable("authorId") final Long authorId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        try {
            Authors authorResponse = authorService.confirmAuthor(currentUser, authorId);
            return ResponseEntity.ok(authorResponse);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(Routing.AUTHORS)
    public ResponseEntity<?> getAllAuthors(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        if (pageNumber != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return ResponseEntity.ok(authorService.getAllAuthorsPageable(pageable));
        }
        List<Authors> authorResponse = authorService.getAllAuthors();
        return ResponseEntity.ok(authorResponse);
    }

    @DeleteMapping(Routing.AUTHOR_BY_ID)
    public ResponseEntity<?> deleteAuthorById(@PathVariable("authorId") final Long authorId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        try {
            authorService.deleteAuthorById(currentUser, authorId);
            return ResponseEntity.ok("Successfully");
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(Routing.AUTHOR_BY_ID)
    public ResponseEntity<?> getAuthorById(@PathVariable("authorId") final Long authorId) {
        return ResponseEntity.ok(authorService.getAuthorsById(authorId));
    }

    @GetMapping(Routing.AUTHOR_BY_USERID)
    public ResponseEntity<?> getAuthorByUserId(@PathVariable("userId") final Long userId) {
        Authors author = authorService.getAuthorsByUserId(userId);
        return ResponseEntity.ok(author);
    }

    @GetMapping(Routing.AUTHOR_ME)
    public ResponseEntity<?> getAuthorMe() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        Authors author = authorService.getAuthorsByUserId(currentUser.getId());
        return ResponseEntity.ok(author);
    }
}
