package com.example.demo.controllers;

import com.example.demo.repository.User;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return this.userService.getAllUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping(path = "{id}")
    public User update(
        @PathVariable(name = "id") Long id,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String name
    ) {
        return userService.updateUser(id, name, email);
    }
}
