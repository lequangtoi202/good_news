package com.quangtoi.good_news.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Tag implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "tagId")
    private Set<ArticleTag> articleTags;
}
