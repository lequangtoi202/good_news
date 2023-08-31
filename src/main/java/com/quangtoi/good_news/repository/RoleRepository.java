package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r inner join UserRole ur on ur.role.id = r.id where ur.user.id=:userId")
    List<Role> getAllByUser(Long userId);

    Role getByName(String name);
}
