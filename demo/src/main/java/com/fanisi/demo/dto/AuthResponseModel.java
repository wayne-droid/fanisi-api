package com.fanisi.demo.dto;

import com.fanisi.demo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseModel {
    private int status_code;
    private String message;
    private User user;
    private String token;
}
