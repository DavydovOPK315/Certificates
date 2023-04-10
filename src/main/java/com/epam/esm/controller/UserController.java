package com.epam.esm.controller;


import com.epam.esm.dto.UserRequestModel;
import com.epam.esm.dto.UserResponseModel;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * To get all users
     *
     * @return ResponseEntity with found users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseModel>> getAll() {
        List<UserResponseModel> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    /**
     * To get user by id
     *
     * @param id user id
     * @return ResponseEntity with found user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> getById(@PathVariable long id) {
        UserResponseModel userResponseModel = userService.getUserById(id);
        return ResponseEntity.ok(userResponseModel);
    }

    /**
     * To create user
     *
     * @param userRequestModel user request model
     * @return status of operation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody UserRequestModel userRequestModel) {
        userService.create(userRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
