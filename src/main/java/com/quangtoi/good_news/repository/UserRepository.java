package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
