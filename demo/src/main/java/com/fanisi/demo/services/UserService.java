package com.fanisi.demo.services;

import com.fanisi.demo.dto.*;
import com.fanisi.demo.models.User;
import com.fanisi.demo.repositories.UserRepository;
import com.fanisi.demo.utils.JWTUtils;
import com.fanisi.demo.utils.ValidatePhonenumber;
import jakarta.jws.soap.SOAPBinding;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

@Log
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    ValidatePhonenumber validatePhonenumber;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseObj createUser(User user) throws Exception
    {
        if (validatePhonenumber.validatePhoneNumber(user.getPhone_number(), "KE"))
        {
            user.setPhone_number(validatePhonenumber.formatNumber(user.getPhone_number()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreated_at(LocalDateTime.now());
            user.setDeleted("1");
            log.log(Level.INFO, "user " + user);
            userRepository.save(user);
            return new ResponseObj(0, "User created successfully");
        }
        else
            return new ResponseObj(100, "Invalid phone number");
    }

    public UserListResponse fetchUsers()
    {
        List<User> userList = userRepository.findAllUsers();

        userList =  userList.stream().peek(user -> {
            user.setPassword("");
        }).toList();

        return new UserListResponse(userList, userList.size());
    }

    public UserResponse fetchUser(int id)
    {
        User user = userRepository.findUserById(id);
        if (user != null)
        {
            user.setPassword("");
            return new UserResponse(user, "User found");
        }

        return new UserResponse(null, "User not found");
    }

    public ResponseObj updateUser(int id, User _user)
    {
        if (validatePhonenumber.validatePhoneNumber(_user.getPhone_number(), "KE"))
        {
            User user = userRepository.findUserById(id);
            _user.setId(user.getId());
            _user.setPassword(user.getPassword());
            _user.setUpdated_at(LocalDateTime.now());
            _user.setCreated_at(user.getCreated_at());
            _user.setLast_login(user.getLast_login());
            _user.setDeleted(user.getDeleted());
            userRepository.save(_user);

            return new ResponseObj(0, "User updated successfully");
        }
        else
            return new ResponseObj(100, "Invalid phone number");
    }
    public ResponseObj deleteUser(int id)
    {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            user.setDeleted("0");
        userRepository.save(user);

        return new ResponseObj(0, "User deleted successfully");
    }

    public AuthResponseModel authenticateUser(AuthRequest authRequest)
    {
        String user_password = "";

        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (user != null)
        {
            user_password = user.getPassword();
        }
        else
        {
            log.log(Level.SEVERE,"Email provided doesn't exist => " + authRequest.getEmail());
            return new AuthResponseModel(100,"Log in failed, check credentials",null,null);
        }

        if (passwordEncoder.matches(authRequest.getPassword(),user_password))
        {
            String token = jwtUtils.generateToken(authRequest.getEmail());
            user.setLast_login(LocalDateTime.now());
            userRepository.save(user);
            user.setPassword("");
            return new AuthResponseModel(0,"User Logged in successfully",user,token);
        }
        else
        {
            log.log(Level.SEVERE,"Wrong Password=> " + authRequest.getEmail());
            return new AuthResponseModel(100,"Log in failed, check credentials",null,null);
        }
    }
}
