package linkedhu_ceng.finalVersion.controller;


import linkedhu_ceng.finalVersion.dto.PasswordDto;
import linkedhu_ceng.finalVersion.dto.SignUpDto;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PutMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody PasswordDto passwordDto){

        User user = userService.getUser();
        System.out.println(passwordEncoder.encode(passwordDto.getOldPassword()));
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.encode("0000"));
        if(passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())
                && passwordDto.getNewPassword().equals(passwordDto.getConfirmation())){
            userService.updatePassword(passwordDto);
            return ResponseEntity.ok("Password updated successfully");
        }
        else{
        return new ResponseEntity<>("Problem with password!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/multiValue")
    public void multiValue(
            @RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(key + " " + value);;
        });
    }
}
