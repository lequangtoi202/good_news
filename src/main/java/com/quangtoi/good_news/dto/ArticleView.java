package com.quangtoi.good_news.dto;

import com.quangtoi.good_news.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleView {
    private Article article;
    private long viewCount;
}
