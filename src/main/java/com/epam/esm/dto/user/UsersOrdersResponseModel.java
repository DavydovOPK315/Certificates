package com.epam.esm.dto.user;

import com.epam.esm.dto.order.OrderResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersOrdersResponseModel {
    private Long id;
    private String login;
    private String email;
    private List<OrderResponseModel> orders;
}
