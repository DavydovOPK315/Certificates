//package com.epam.esm.service;
//
//import com.epam.esm.dto.user.UserRequestModel;
//import com.epam.esm.dto.user.UserResponseModel;
//import com.epam.esm.dto.user.UsersOrdersResponseModel;
//import com.epam.esm.entity.User;
//import com.epam.esm.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @Mock
//    private UserRepository userRepository;
//
//    private final User user = new User();
//
//    @Spy
//    @InjectMocks
//    private UserService service;
//
//    @Test
//    void getAll() {
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        when(userRepository.findAll(anyInt(), anyInt()))
//                .thenReturn(users);
//
//        assertEquals(1, service.getAll(anyInt(), anyInt()).size());
//
//        verify(service, times(1)).getAll(anyInt(), anyInt());
//        verify(userRepository, times(1)).findAll(anyInt(), anyInt());
//    }
//
//    @Test
//    void getUserById() {
//        when(userRepository.findById(anyLong()))
//                .thenReturn(user);
//        when(modelMapper.map(user, UserResponseModel.class))
//                .thenReturn(new UserResponseModel());
//
//        assertNotNull(service.getUserById(anyLong()));
//
//        verify(service, times(1)).getUserById(anyLong());
//        verify(userRepository, times(1)).findById(anyLong());
//        verify(modelMapper, times(1)).map(user, UserResponseModel.class);
//    }
//
//    @Test
//    void getUsersOrdersById() {
//        user.setOrders(new ArrayList<>());
//        when(userRepository.findById(anyLong()))
//                .thenReturn(user);
//        when(modelMapper.map(user, UsersOrdersResponseModel.class))
//                .thenReturn(new UsersOrdersResponseModel());
//
//        assertNotNull(service.getUsersOrdersById(anyLong()));
//
//        verify(service, times(1)).getUsersOrdersById(anyLong());
//        verify(userRepository, times(1)).findById(anyLong());
//        verify(modelMapper, times(1)).map(user, UsersOrdersResponseModel.class);
//    }
//
//    @Test
//    void create() {
//        UserRequestModel model = new UserRequestModel();
//        when(modelMapper.map(model, User.class))
//                .thenReturn(user);
//        doNothing().when(userRepository).save(user);
//
//        assertDoesNotThrow(() -> service.create(model));
//
//        verify(service, times(1)).create(model);
//        verify(modelMapper, times(1)).map(model, User.class);
//    }
//}