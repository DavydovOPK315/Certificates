package com.epam.esm.service;

import com.epam.esm.dto.OrderResponseModel;
import com.epam.esm.dto.UserRequestModel;
import com.epam.esm.dto.UserResponseModel;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<UserResponseModel> getAll(int pageNumber, int pageSize) {
        List<User> users = userRepository.findAll(pageNumber, pageSize);
        return mapList(users, UserResponseModel.class);
    }

    public UserResponseModel getUserById(long id) {
        User user = userRepository.findById(id);
        List<Order> orders = user.getOrders();
        UserResponseModel userResponseModel = modelMapper.map(user, UserResponseModel.class);
        userResponseModel.setOrders(mapList(orders, OrderResponseModel.class));
        return userResponseModel;
    }

    public void create(UserRequestModel userRequestModel) {
        User user = modelMapper.map(userRequestModel, User.class);
        user.setId(null);
        userRepository.save(user);
    }

    private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
