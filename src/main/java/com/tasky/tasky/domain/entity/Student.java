package com.tasky.tasky.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private int id;
    private String name;
    private int marks;
}
