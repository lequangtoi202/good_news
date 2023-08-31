package com.quangtoi.good_news.dto;

import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.Category;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String description;
    private String status;
    private String source;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long categoryId;
    private Long authorsId;
}
