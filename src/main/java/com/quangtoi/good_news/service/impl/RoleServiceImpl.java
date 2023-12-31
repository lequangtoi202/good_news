package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.service.RoleService;
import com.quangtoi.good_news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role, Long roleId) {
        Role roleSaved = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        roleSaved.setName(role.getName());
        return roleRepository.save(roleSaved);
    }

    @Override
    public void deleteRole(Long id) {
        Role roleSaved = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(roleSaved);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> getAllRolesPageable(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
    }

    @Override
    public List<Role> getAllRoleOfUser(Long userId) {
        UserResponse user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return roleRepository.getAllByUser(userId);
    }
}
