package iosecurity.springboot_basicsecurity.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "home";
    }
    
}
