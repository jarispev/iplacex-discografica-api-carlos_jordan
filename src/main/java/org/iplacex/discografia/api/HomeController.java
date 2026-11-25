package org.iplacex.discografia.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String bienvenida() {
        return "Bienvenido a la discográfica Iplacex";
    }
}
