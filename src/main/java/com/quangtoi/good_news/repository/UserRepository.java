package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActive(boolean isActive);

    User findByUsernameAndActive(String username, boolean isActive);

    Optional<User> findByEmailAndActive(String email, boolean isActive);

    Optional<User> findByPasswordResetToken(String token);

    boolean existsByUsernameAndActive(String username, boolean isActive);

    boolean existsByEmailAndActive(String email, boolean isActive);
}
