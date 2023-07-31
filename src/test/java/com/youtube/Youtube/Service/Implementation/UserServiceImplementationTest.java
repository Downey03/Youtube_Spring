package com.youtube.Youtube.Service.Implementation;

import com.youtube.Youtube.DTO.UserDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserServiceImplementationTest {

    static UserServiceImplementation userFunction = new UserServiceImplementation();
    static UserRepositoryTest userTest = new UserRepositoryTest();


    @Test
    void thisShouldCreateAUser() {
        UserDTO user = UserDTO.builder()
                .userEmail("hello123@gmail.com")
                .userName("hello")
                .password("hello123")
                .build();

        System.out.println(user);
        userFunction.createUser(user);

        Boolean expectedResult = userTest.findExistingUser(user.getUserEmail());

        assertThat(expectedResult).isTrue();



    }

    @Test
    void loginUser() {
    }
}