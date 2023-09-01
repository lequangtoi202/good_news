package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.CategoryService;
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
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/api/v1/categories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("cateId") Long cateId) {
        return ResponseEntity.ok(categoryService.getCategoryById(cateId));
    }

    @PostMapping("/api/v1/categories")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable("cateId") Long cateId) {
        return ResponseEntity.ok(categoryService.updateCategory(category, cateId));
    }

    @PutMapping("/api/v1/categories/{cateId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("cateId") Long cateId) {
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
