package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    Role addRole(Role role);

    Role updateRole(Role role, Long roleId);

    void deleteRole(Long id);

    List<Role> getAllRoles();

    Page<Role> getAllRolesPageable(Pageable pageable);

    Role getRoleById(Long roleId);

    List<Role> getAllRoleOfUser(Long userId);
}
