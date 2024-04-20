package com.fanisi.demo.controllers;

import com.fanisi.demo.dto.AuthRequest;
import com.fanisi.demo.dto.AuthResponseModel;
import com.fanisi.demo.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@Log
@RestController
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    UserService userService;

    @RequestMapping(
            value = "/generate/token",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity loginUser(@RequestBody AuthRequest authRequest)
    {
        log.log(Level.INFO, "authrequest " + authRequest.getEmail());
        AuthResponseModel authResponseModel = userService.authenticateUser(authRequest);
        return ResponseEntity.ok(authResponseModel);
    }
}
