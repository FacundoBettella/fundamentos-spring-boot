package com.fundamentos.springboot.fundamentos.configuration;

import com.fundamentos.springboot.fundamentos.caseuse.*;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }

    @Bean
    CreateUser newUser(UserService userService){
        return new CreateUser(userService);
    }

    @Bean
    DeleteUser deleteUser(UserService userService) {
        return new DeleteUser(userService);
    }

    @Bean
    UpdateUser updateUser(UserService userService) {
        return new UpdateUser(userService);
    }

}
