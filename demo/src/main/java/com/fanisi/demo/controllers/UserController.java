package com.fanisi.demo.controllers;

import com.fanisi.demo.dto.ResponseObj;
import com.fanisi.demo.dto.UserListResponse;
import com.fanisi.demo.dto.UserResponse;
import com.fanisi.demo.models.User;
import com.fanisi.demo.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(
            value = "/new/user",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity newUser(@RequestBody User user) throws Exception {
        ResponseObj responseObj = userService.createUser(user);
        return ResponseEntity.ok(responseObj);
    }

    @RequestMapping(
            value = "/fetch/user/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity fetchUser(@PathVariable int id)
    {
        UserResponse userResponse = userService.fetchUser(id);
        return ResponseEntity.ok(userResponse);
    }

    @RequestMapping(
            value = "/fetch/users",
            method = RequestMethod.GET
    )
    public ResponseEntity fetchUsers()
    {
        UserListResponse userListResponse = userService.fetchUsers();
        return ResponseEntity.ok(userListResponse);
    }

    @RequestMapping(
            value = "/update/user/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody User user)
    {
        ResponseObj responseObj = userService.updateUser(id, user);
        return ResponseEntity.ok(responseObj);
    }

    @RequestMapping(
            value = "/delete/user/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity deleteUser(@PathVariable int id)
    {
        ResponseObj responseObj = userService.deleteUser(id);
        return ResponseEntity.ok(responseObj);
    }
}
