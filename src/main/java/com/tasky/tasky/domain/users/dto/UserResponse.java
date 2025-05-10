package com.tasky.tasky.domain.users.dto;

import com.tasky.tasky.domain.users.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    String username;
//     token info
}
