package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.CategoryService;
import com.quangtoi.good_news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/api/v1/categories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("cateId") final Long cateId) {
        return ResponseEntity.ok(categoryService.getCategoryById(cateId));
    }

    @PostMapping(value = "/api/v1/categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addCategory(@RequestParam("category") final String category,
                                         @RequestPart("image") final MultipartFile image) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Category req = objectMapper.readValue(category, Category.class);
        return ResponseEntity.ok(categoryService.addCategory(req, image));
    }

    @PutMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> updateCategory(@RequestBody final Category category, @PathVariable("cateId") final Long cateId) {
        return ResponseEntity.ok(categoryService.updateCategory(category, cateId));
    }

    @DeleteMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("cateId") final Long cateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    categoryService.deleteCategory(cateId);
                    return ResponseEntity.ok("Successfully");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
