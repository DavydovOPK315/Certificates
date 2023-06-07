package com.epam.esm.service;

import com.epam.esm.dto.user.UserRequestModel;
import com.epam.esm.dto.user.UserResponseModel;
import com.epam.esm.dto.user.UsersOrdersResponseModel;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final User user = new User();

    @Spy
    @InjectMocks
    private UserService service;

    @Test
    void getAll() {
        List<User> users = new ArrayList<>();
        users.add(user);

        @SuppressWarnings("unchecked")
        Page<User> page = mock(Page.class);

        when(userRepository.findAll(PageRequest.of(10, 10)))
                .thenReturn(page);
        when(page.toList())
                .thenReturn(users);

        assertEquals(1, service.getAll(2, 10).size());

        verify(service, times(1)).getAll(anyInt(), anyInt());
        verify(userRepository, times(1)).findAll(PageRequest.of(10, 10));
    }

    @Test
    void getUserById() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseModel.class))
                .thenReturn(new UserResponseModel());

        assertNotNull(service.getUserById(anyLong()));

        verify(service, times(1)).getUserById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(user, UserResponseModel.class);
    }

    @Test
    void getUsersOrdersById() {
        user.setOrders(new ArrayList<>());
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(modelMapper.map(user, UsersOrdersResponseModel.class))
                .thenReturn(new UsersOrdersResponseModel());

        assertNotNull(service.getUsersOrdersById(anyLong()));

        verify(service, times(1)).getUsersOrdersById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(user, UsersOrdersResponseModel.class);
    }

    @Test
    void create() {
        UserRequestModel model = new UserRequestModel();
        model.setPassword("password");

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodePassword");
        when(modelMapper.map(model, User.class))
                .thenReturn(user);
        when(userRepository.save(user))
                .thenReturn(user);

        assertDoesNotThrow(() -> service.create(model));

        verify(service, times(1)).create(model);
        verify(modelMapper, times(1)).map(model, User.class);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void getUserByEmailFailed() {
        String wrongEmail = "wrongEmail@gmail.com";
        when(userRepository.findByEmail(wrongEmail))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getUserByEmail(wrongEmail));

        verify(service, times(1)).getUserByEmail(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseModel.class))
                .thenReturn(new UserResponseModel());

        assertNotNull(service.getUserByEmail(anyString()));

        verify(service, times(1)).getUserByEmail(anyString());
        verify(modelMapper, times(1)).map(user, UserResponseModel.class);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}