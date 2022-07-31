package com.fundamentos.springboot.fundamentos.controller;

/* Servicios rest consumidos por el cliente */
/* @RestController permite que todos los servicios se formateen en formato JSON */

import com.fundamentos.springboot.fundamentos.caseuse.*;
import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private GetUser getUser;
    private GetPageableUsers getPageableUsers;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;

    public UserRestController(GetUser getUser, CreateUser createUser, DeleteUser deleteUser, UpdateUser updateUser, GetPageableUsers getPageableUsers) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.getPageableUsers = getPageableUsers;
    }

    @GetMapping("/")
    List<User> get() {
        return getUser.getAll();
    }

    @PostMapping("/createUser")
    ResponseEntity<User> newUser(@RequestBody User newUser) {
        /* HttpStatus.CREATED retorna status 201 cuando el usuario se ha creado correctamente */
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteUserById/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        deleteUser.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateUserById/{id}")
    ResponseEntity<User> update(@RequestBody User userToUpdate, @PathVariable Long id) {
        return new ResponseEntity<>(updateUser.update(userToUpdate, id), HttpStatus.OK);
    }

    /* Paginacion */
    @GetMapping("/pageable")
    List <User> getUserPageable(@RequestParam int page, @RequestParam int size) {
        return getPageableUsers.getUsers(page, size);
    }

}
