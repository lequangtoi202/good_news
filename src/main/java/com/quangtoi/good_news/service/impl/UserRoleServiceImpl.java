package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserRole;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.repository.UserRoleRepository;
import com.quangtoi.good_news.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
        return "Gán quyền thành công";
    }

    @Override
    public String retrieveRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        UserRole userRole = userRoleRepository.findByUserAndRole(user, role);
        userRoleRepository.delete(userRole);
        return "Xóa quyền thành công";
    }
}
