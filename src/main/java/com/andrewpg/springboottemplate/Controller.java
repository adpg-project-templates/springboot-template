package com.andrewpg.springboottemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

        @GetMapping("/hello")
        public String hello() {
            return "Hello, Spring dasdasdsdwwwww!";
        }

        @GetMapping("/goodbye")
        public String goodbye() {
            return "Goodbye, Spring Bofffot!";
        }
}
