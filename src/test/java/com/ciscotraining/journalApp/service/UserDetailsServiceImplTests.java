package com.ciscotraining.journalApp.service;

import com.ciscotraining.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ciscotraining.journalApp.entity.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//    @SpringBootTest
public class UserDetailsServiceImplTests {

//    @Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

//    @MockitoBean
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void loadUserByUserNameTests() {
        when(userRepository.findByUserName(anyString())).thenReturn(User.builder().userName("Siddhant").password("Siddhant").roles(new ArrayList<>()).build());
        UserDetails user=  userDetailsService.loadUserByUsername("Siddhant");
        Assertions.assertNotNull(user);

    }
}
