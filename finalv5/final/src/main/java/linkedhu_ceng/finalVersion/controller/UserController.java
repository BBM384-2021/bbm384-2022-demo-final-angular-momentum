package linkedhu_ceng.finalVersion.controller;


import linkedhu_ceng.finalVersion.dto.SignUpDto;
import linkedhu_ceng.finalVersion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/multiValue")
    public void multiValue(
            @RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(key + " " + value);;
        });
    }
}