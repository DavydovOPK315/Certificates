package com.epam.esm.controller;

import com.epam.esm.dto.user.UserLoginRequestModel;
import com.epam.esm.dto.user.UserRequestModel;
import com.epam.esm.dto.user.UserResponseModel;
import com.epam.esm.dto.user.UsersOrdersResponseModel;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

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
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody UserRequestModel userRequestModel) {
        userService.create(userRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * To authorize user
     *
     * @param userLoginRequestModel user login request model
     * @return status of operation
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestModel userLoginRequestModel) {
        return authService.login(userLoginRequestModel);
    }

    @GetMapping("/private")
    public String privatePage(Authentication authentication) {
        System.out.println("Auth\n\t" + authentication.getCredentials());
        return "Welcome to the VIP room ~[ " +
                getName(authentication)
                + " ]~'\u263A'" + '\u264A';
    }

    @GetMapping("/private2")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        principal.getAttributes().forEach((s, o) -> System.out.println(s + " " + o));
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }


    private static String getName(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }
}
