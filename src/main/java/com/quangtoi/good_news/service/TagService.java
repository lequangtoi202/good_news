package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Tag;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tagReq);

    Tag updateTag(Tag tagReq, Long tagId);

    void deleteTag(Long tagId);

    List<Tag> getAllTags();

    List<Tag> getAllTagsOfArticle(Long articleId);

    Tag getTagById(Long tagId);
}
