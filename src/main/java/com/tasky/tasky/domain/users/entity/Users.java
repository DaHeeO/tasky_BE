package com.tasky.tasky.domain.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer usrId;
    private String uid;
    private String password;
    private String username;
    private String email;
    @Column(name="profile_image")
    private String profileImage;
    private Provider provider;
}
