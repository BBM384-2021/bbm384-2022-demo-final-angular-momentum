package linkedhu_ceng.demo.controller;

import linkedhu_ceng.demo.dto.LoginDto;
import linkedhu_ceng.demo.dto.SignUpDto;
import linkedhu_ceng.demo.entity.Role;
import linkedhu_ceng.demo.entity.User;
import linkedhu_ceng.demo.repository.RoleRepository;
import linkedhu_ceng.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    User user;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){

        if(!userRepository.existsByUserId(loginDto.getUserId())){
            return new ResponseEntity<>("User with ID: " + loginDto.getUserId() +" does not exist!", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserId(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setSurname(signUpDto.getSurname());
        user.setPhoneNumber(signUpDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

    @GetMapping("/signup/success")
    public Map<String,Object> success(){
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", user.getUserId());
        model.put("nameSurname", user.getName() + " " + user.getSurname());
        return model;
    }


    /*@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        URL url = new URL(request.getRequestURL().toString());
        String urlStr = url.getProtocol() + "://" + url.getAuthority();
        return "redirect:" + ssoServiceUrl + "/logout.do?redirect=" + urlStr + "&clientId=" + clientId;
    }*/
}
