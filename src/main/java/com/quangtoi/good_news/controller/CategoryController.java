package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.CategoryService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping(Routing.CATEGORIES)
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        if (pageNumber != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return ResponseEntity.ok(categoryService.getAllCategoriesPageable(pageable));
        }
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @GetMapping(Routing.CATEGORY_BY_ID)
    public ResponseEntity<?> getCategoryById(@PathVariable("cateId") final Long cateId) {
        return ResponseEntity.ok(categoryService.getCategoryById(cateId));
    }

    @PostMapping(value = Routing.CATEGORIES, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addCategory(@RequestParam("category") final String category,
                                         @RequestPart("image") final MultipartFile image) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Category req = objectMapper.readValue(category, Category.class);
        return ResponseEntity.ok(categoryService.addCategory(req, image));
    }

    @PutMapping(value = Routing.CATEGORY_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategory(@PathVariable("cateId") final Long cateId,
                                            @RequestParam("categoryReq") final String categoryReq,
                                            @RequestPart("image") final MultipartFile image) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Category category = objectMapper.readValue(categoryReq, Category.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(categoryService.updateCategory(category, cateId, image));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping(Routing.CATEGORY_BY_ID)
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
