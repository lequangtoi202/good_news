package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.dto.enumeration.CacheName;
import com.quangtoi.good_news.pojo.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByActive(boolean isActive, Pageable pageable);

    @Override
    @CacheEvict(cacheManager = "defaultCache", cacheNames = CacheName.Fields.USER, key = "#user.username")
    User save(User user);

    @Cacheable(cacheManager = "defaultCache", cacheNames = CacheName.Fields.USER, unless = "#result == null")
    User findByUsernameAndActive(String username, boolean isActive);

    Optional<User> findByEmailAndActive(String email, boolean isActive);

    Optional<User> findByPasswordResetToken(String token);

    boolean existsByUsernameAndActive(String username, boolean isActive);

    boolean existsByEmailAndActive(String email, boolean isActive);

    @Query("select u from User u inner join UserTag ut on ut.userId = u.id where ut.tagId=:tagId")
    List<User> getAllUsersByTag(@Param("tagId") Long tagId);
}
