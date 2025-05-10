package com.tasky.tasky.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long usrId;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @Column(name = "provider_id")
    private String providerId;
    private String name;
    private String email;
    @Column(name="profile_image")
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private Role role;
}
