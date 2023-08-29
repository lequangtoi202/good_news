package com.quangtoi.good_news.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Notification implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "content", length = -1)
    private String content;

    @Basic
    @Column(name = "type", length = 45)
    private String type;

    @Basic
    @Column(name = "is_sent")
    private Byte isSent;

    @Basic
    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
