package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    UserTag findByUserIdAndTagId(Long userId, Long tagId);


}
