package linkedhu_ceng.demo.controller;


import linkedhu_ceng.demo.dto.SignUpDto;
import linkedhu_ceng.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/details")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(userService.updateUser(signUpDto));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/delete")
    public void deleteUser(){
        userService.deleteUser();
    }
}