package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.UserTag;

public interface UserTagService {
    UserTag followTag(Long tagId, Long userId);

    UserTag getFollowStatus(Long tagId, Long userId) throws Exception;
}
