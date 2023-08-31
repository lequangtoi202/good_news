package com.quangtoi.good_news.service;

public interface UserRoleService {
    String assignRoleToUser(Long userId, Long roleId);

    String retrieveRoleFromUser(Long userId, Long roleId);
}
