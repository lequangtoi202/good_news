package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Authors, Long> {
    Authors findByUser(User user);
}
