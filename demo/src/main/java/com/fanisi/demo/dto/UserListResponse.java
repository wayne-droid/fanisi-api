package com.fanisi.demo.dto;

import com.fanisi.demo.models.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    List<User> users;
    int count;
}
