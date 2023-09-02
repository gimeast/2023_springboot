package iosecurity.springboot_basicsecurity.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SecurityController {

    @PostMapping("/")
    public String index() {
        return "home";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping("/denied")
    public String deniedPage() {
        return "deniedPage";
    };

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin/**")
    public String admin(){
        return "admin and sys";
    }

    @GetMapping("/admin/pay")
    public String adminPay(){
        return "admin pay";
    }

}
