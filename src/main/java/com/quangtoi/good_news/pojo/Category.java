package com.quangtoi.good_news.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Category implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", length = 45)
    private String name;

    @Basic
    @Column(name = "description", length = -1)
    private String description;

    @Basic
    @Column(name = "is_active")
    private Byte isActive;

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "categoryId")
    private Set<Article> articles;
}
