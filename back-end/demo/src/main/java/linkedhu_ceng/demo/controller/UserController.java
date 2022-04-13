package linkedhu_ceng.demo.controller;


import linkedhu_ceng.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    /***
    @GetMapping("/details")
    public Object userDetails(Authentication authentication) {
        return authentication.getPrincipal();
    }***/

    @Autowired
    UserService userService;

    @GetMapping("details")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser());
    }
}