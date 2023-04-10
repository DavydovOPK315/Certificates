package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
    private Long id;
    private String login;
    private String email;
    private List<OrderResponseModel> orders;
}
