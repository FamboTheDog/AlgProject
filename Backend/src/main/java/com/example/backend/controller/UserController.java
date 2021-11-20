package com.example.backend.controller;

import com.example.backend.db.User;
import com.example.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("register")
    public User register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("getAll")
    public List<User> getUsers() {
        return userService.getAll();
    }

}
