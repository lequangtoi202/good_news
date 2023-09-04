package com.quangtoi.good_news.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

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
    @Column(name = "status", length = 50)
    private String status;

    @Basic
    @Column(name = "source")
    private String source;

    @Basic
    @Column(name = "active")
    private boolean active;

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "authors_id", nullable = false)
    private Authors authors;

    @JsonIgnore
    @OneToMany(mappedBy = "articleId")
    private Set<ArticleTag> articleTags;
}
