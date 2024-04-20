package com.fanisi.demo.dto;

import com.fanisi.demo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    User user;
    String message;
}
