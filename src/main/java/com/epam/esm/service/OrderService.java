package com.epam.esm.service;

import com.epam.esm.dto.order.OrderRequestModel;
import com.epam.esm.dto.order.OrderResponseModel;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;
    private final ModelMapper modelMapper;

    public void create(OrderRequestModel orderRequestModel) {
        Order order = new Order();
        String date = LocalDateTime.now().toString();
        User user = userRepository.findById(orderRequestModel.getUserId()).orElse(null);
        Certificate certificate = certificateRepository.findById(orderRequestModel.getCertificateId());

        if (user == null || certificate == null) {
            throw new ResourceNotFoundException("Unable to create order due to wrong user or certificate data");
        }
        order.setCreateDate(date);
        order.setPrice(certificate.getPrice());
        order.setCertificate(certificate);
        order.setUser(user);
        orderRepository.save(order);
    }

    public OrderResponseModel getOrderById(long id) {
        Order order = orderRepository.findById(id);
        return modelMapper.map(order, OrderResponseModel.class);
    }
}