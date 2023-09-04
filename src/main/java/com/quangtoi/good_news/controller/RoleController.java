package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.RoleService;
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
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping("/api/v1/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/api/v1/roles/{roleId}")
    public ResponseEntity<?> getRoleById(@PathVariable("roleId") final Long roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @GetMapping("/api/v1/users/{userId}/roles")
    public ResponseEntity<?> getAllRolesOfUser(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(roleService.getAllRoleOfUser(userId));
    }

    @PostMapping("/api/v1/roles")
    public ResponseEntity<?> addRole(@RequestBody final Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(roleService.addRole(role));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/roles/{roleId}")
    public ResponseEntity<?> updateRole(@PathVariable("roleId") final Long roleId, @RequestBody final Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    return ResponseEntity.ok(roleService.updateRole(role, roleId));
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable("roleId") final Long roleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    roleService.deleteRole(roleId);
                    return ResponseEntity.ok("Successfully");
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
