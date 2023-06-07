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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @Spy
    @InjectMocks
    private OrderService service;

    private final Order order = new Order();

    @Test
    void create() {
        OrderRequestModel orderRequestModel = new OrderRequestModel(1L, 2L);
        Certificate certificate = new Certificate();
        User user = new User();

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        when(certificateRepository.findById(anyLong()))
                .thenReturn(certificate);
        doNothing().when(orderRepository).save(any(Order.class));

        assertDoesNotThrow(() -> service.create(orderRequestModel));
    }

    @Test
    void createWithResourceNotFoundException() {
        OrderRequestModel orderRequestModel = new OrderRequestModel(1L, 2L);

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        when(certificateRepository.findById(anyLong()))
                .thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(orderRequestModel));
    }

    @Test
    void getOrderById() {
        when(orderRepository.findById(anyLong()))
                .thenReturn(order);
        when(modelMapper.map(order, OrderResponseModel.class))
                .thenReturn(new OrderResponseModel());

        assertNotNull(service.getOrderById(anyInt()));
    }
}