package com.quangtoi.good_news.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "username", length = 45)
    private String username;

    @Basic
    @Column(name = "password", length = 255)
    private String password;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "is_active")
    private boolean active;

    @Basic
    @Column(name = "full_name", length = 50)
    private String fullName;

    @Basic
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Basic
    @Column(name = "address", length = 45)
    private String address;

    @Basic
    @Column(name = "email", length = 45)
    private String email;

    @Basic
    @Column(name = "password_reset_token", length = 50)
    private String passwordResetToken;

    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<UserNotification> userNotifications;
}
