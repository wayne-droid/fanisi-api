package com.fanisi.demo.repositories;

import com.fanisi.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.deleted='1'")
    List<User> findAllUsers();

    @Query("select u from User u where u.id=:id and u.deleted='1'")
    User findUserById(int id);
}
