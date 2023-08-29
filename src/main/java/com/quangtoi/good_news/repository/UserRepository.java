package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
