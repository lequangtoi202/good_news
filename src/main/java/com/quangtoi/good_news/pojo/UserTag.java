package com.quangtoi.good_news.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tag")
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_on", nullable = true)
    private Timestamp createdOn;

    @Column(name = "updated_on", nullable = true)
    private Timestamp updatedOn;
}

