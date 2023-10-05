package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.dto.AuthorResponse;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.AuthorRequest;
import com.quangtoi.good_news.service.AuthorService;
import com.quangtoi.good_news.service.UserService;
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

    @PostMapping("/api/v1/authors")
    public ResponseEntity<?> registerAuthor(@RequestBody final AuthorRequest authorRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        AuthorResponse authorResponse = authorService.registerAuthor(currentUser, authorRequest);
                        return ResponseEntity.ok(authorResponse);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/authors")
    public ResponseEntity<?> updateAuthor(@RequestBody final AuthorRequest authorRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    AuthorResponse authorResponse = authorService.updateAuthor(currentUser, authorRequest);
                    return ResponseEntity.ok(authorResponse);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/authors/{authorId}")
    public ResponseEntity<?> updateAuthorById(@RequestBody final AuthorRequest authorRequest, @PathVariable("authorId") final Long authorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    Authors authorUpdated = authorService.updateAuthorById(currentUser, authorRequest, authorId);
                    return ResponseEntity.ok(authorUpdated);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/v1/authors/{authorId}/confirm")
    public ResponseEntity<?> confirmAuthor(@PathVariable("authorId") final Long authorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        Authors authorResponse = authorService.confirmAuthor(currentUser, authorId);
                        return ResponseEntity.ok(authorResponse);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/authors")
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

    @DeleteMapping("/api/v1/authors/{authorId}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("authorId") final Long authorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        authorService.deleteAuthorById(currentUser, authorId);
                        return ResponseEntity.ok("Successfully");
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/authors/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable("authorId") final Long authorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        return ResponseEntity.ok(authorService.getAuthorsById(authorId));
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/v1/users/{userId}/authors")
    public ResponseEntity<?> getAuthorByUserId(@PathVariable("userId") final Long userId) {
        Authors author = authorService.getAuthorsByUserId(userId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/api/v1/authors/me")
    public ResponseEntity<?> getAuthorMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        Authors author = authorService.getAuthorsByUserId(currentUser.getId());
                        return ResponseEntity.ok(author);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
