package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserAndRole(User user, Role role);
}
