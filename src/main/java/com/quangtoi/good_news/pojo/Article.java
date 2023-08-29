package com.quangtoi.good_news.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "title", length = 150)
    private String title;

    @Basic
    @Column(name = "content", length = -1)
    private String content;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "description", length = -1)
    private String description;

    @Basic
    @Column(name = "status", length = 50)
    private String status;

    @Basic
    @Column(name = "source")
    private String source;

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Basic
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Basic
    @Column(name = "authors_id", nullable = false)
    private Long authorsId;

}
