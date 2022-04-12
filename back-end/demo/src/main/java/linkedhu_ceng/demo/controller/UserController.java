package linkedhu_ceng.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/details")
    public Object userDetails(Authentication authentication) {
        return authentication.getPrincipal();
    }
}