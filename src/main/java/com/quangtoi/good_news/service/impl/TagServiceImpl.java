package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Tag;
import com.quangtoi.good_news.repository.ArticleRepository;
import com.quangtoi.good_news.repository.TagRepository;
import com.quangtoi.good_news.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Tag addTag(Tag tagReq) {
        return tagRepository.save(tagReq);
    }

    @Override
    public Tag updateTag(Tag tagReq, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        tag.setName(tagReq.getName());
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        //may be affect to foreign key in articleTag
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        tagRepository.delete(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        Pageable pageable = PageRequest.of(0, 20);
        return tagRepository.findAllTagLimit(pageable);
    }

    @Override
    public List<Tag> getAllTagsOfArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        return tagRepository.findAllByArticle(article.getId());
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
    }
}
