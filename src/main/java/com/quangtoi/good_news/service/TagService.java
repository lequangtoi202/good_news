package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tagReq);

    Tag updateTag(Tag tagReq, Long tagId);

    void deleteTag(Long tagId);

    List<Tag> getAllTags();

    Page<Tag> getAllTagsPageable(Pageable pageable);

    List<Tag> getAllTagsOfArticle(Long articleId);

    Tag getTagById(Long tagId);
}
