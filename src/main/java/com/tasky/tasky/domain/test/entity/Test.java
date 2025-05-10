package com.tasky.tasky.domain.test.entity;

import com.tasky.tasky.domain.user.entity.Provider;
import com.tasky.tasky.domain.user.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;
}
