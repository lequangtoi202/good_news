package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Role;

import java.util.List;

public interface RoleService {

    Role addRole(Role role);

    Role updateRole(Role role, Long roleId);

    void deleteRole(Long id);

    List<Role> getAllRoles();

    Role getRoleById(Long roleId);
}
