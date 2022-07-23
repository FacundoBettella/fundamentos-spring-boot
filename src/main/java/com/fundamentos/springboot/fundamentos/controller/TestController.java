package com.fundamentos.springboot.fundamentos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping
    @ResponseBody
    public  ResponseEntity<String> function () {
        return new ResponseEntity<>("Hello from controller! Ahora con un cambio (ctrl + f9 => nuevo build y con spring-devtools dependency no hace falta reiniciar el server para apreciar los cambios en el navegador).", HttpStatus.OK);
    }
}
