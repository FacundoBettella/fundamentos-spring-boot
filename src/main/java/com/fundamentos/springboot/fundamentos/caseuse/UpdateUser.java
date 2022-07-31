package com.fundamentos.springboot.fundamentos.caseuse;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.service.UserService;

public class UpdateUser {

    private UserService userService;

    public UpdateUser (UserService userService){
        this.userService = userService;
    }

    public User update (User userToUpdate, Long id) {
        return userService.update(userToUpdate, id);
    }

}
