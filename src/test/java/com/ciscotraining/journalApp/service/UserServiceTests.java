package com.ciscotraining.journalApp.service;

import com.ciscotraining.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
@Disabled
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test

//    @BeforeEach
//    @BeforeAll
//    @AfterEach
//    @AfterAll
//    ArgumentsSource

    public void testFindByUserName() {
        assertNotNull("testFindByUserName", userRepository.findByUserName("Anindya"));
        assertTrue("", 5>2);
    }
    @ParameterizedTest
    @CsvSource({
        "1, 2, 3",
        "2, 10, 12",
        "3, 3, 9"
    })
    public void test(int a , int b, int expected){
        assertEquals("",  expected, a+b);
    }
}
