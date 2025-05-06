package com.tasky.tasky.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
}
