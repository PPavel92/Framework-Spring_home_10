package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(1L, "John Doe", "john.doe@example.com");
    }

    @Test
    public void testGetUserById_Success() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));


        User result = userService.getUserById(1L);


        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testGetUserById_UserNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testSaveUser() {

        when(userRepository.save(testUser)).thenReturn(testUser);


        User result = userService.saveUser(testUser);


        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testDeleteUser() {

        doNothing().when(userRepository).deleteById(1L);


        userService.deleteUser(1L);


        verify(userRepository, times(1)).deleteById(1L);
    }
}
