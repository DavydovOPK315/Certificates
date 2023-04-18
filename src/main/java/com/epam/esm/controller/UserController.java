package com.epam.esm.controller;

import com.epam.esm.dto.user.UserRequestModel;
import com.epam.esm.dto.user.UserResponseModel;
import com.epam.esm.dto.user.UsersOrdersResponseModel;
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
    public ResponseEntity<List<UsersOrdersResponseModel>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                                 @RequestParam(defaultValue = "20") int pageSize) {
        List<UsersOrdersResponseModel> users = userService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok(users);
    }

    /**
     * To get user by id
     *
     * @param id user id
     * @return ResponseEntity with found user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable long id) {
        UserResponseModel userResponseModel = userService.getUserById(id);
        return ResponseEntity.ok(userResponseModel);
    }

    /**
     * To get user by id with orders
     *
     * @param id user id
     * @return ResponseEntity with found user and with his orders
     */
    @GetMapping("/{id}/orders")
    public ResponseEntity<UsersOrdersResponseModel> getUserByIdWithOrders(@PathVariable long id) {
        UsersOrdersResponseModel userResponseModel = userService.getUsersOrdersById(id);
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
