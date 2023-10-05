package com.quangtoi.good_news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleData {
    String title;
    String content;
    String image;
    String source;
    String categoryName;
    String author;
}
