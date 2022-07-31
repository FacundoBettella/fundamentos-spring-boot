package com.fundamentos.springboot.fundamentos.caseuse;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class GetPageableUsers {

    private UserService userService;

    public GetPageableUsers(UserService userService){
        this.userService = userService;
    }

    public List<User> getUsers(int page, int size){
        return userService.getAllPageableUsers(page, size);
    }

}
