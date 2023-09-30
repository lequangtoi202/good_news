package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Tag;
import com.quangtoi.good_news.pojo.UserTag;
import com.quangtoi.good_news.repository.TagRepository;
import com.quangtoi.good_news.repository.UserTagRepository;
import com.quangtoi.good_news.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserTagServiceImpl implements UserTagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserTagRepository userTagRepository;

    @Override
    public UserTag followTag(Long tagId, Long userId) {
        Tag tag  = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        UserTag userTag = userTagRepository.findByUserIdAndTagId(userId, tag.getId());
        if (userTag != null) {
            if (!userTag.isActive()) {
                userTag.setActive(true);
            } else {
                userTag.setActive(false);
            }
            userTag.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
            return userTagRepository.save(userTag);
        } else {
            UserTag newUserTag = new UserTag();
            newUserTag.setUserId(userId);
            newUserTag.setTagId(tag.getId());
            newUserTag.setActive(true);
            newUserTag.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
            newUserTag.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            return userTagRepository.save(newUserTag);
        }

    }

    @Override
    public UserTag getFollowStatus(Long tagId, Long userId) throws Exception {
        Tag tag  = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        UserTag userTag = userTagRepository.findByUserIdAndTagId(userId, tag.getId());
        if (userTag == null) {
            throw new Exception("UserTag not found with user " + userId + " and tag " + tagId);
        }
        return userTag;
    }
}
