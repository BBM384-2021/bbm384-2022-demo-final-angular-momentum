package linkedhu_ceng.finalVersion.controller;

import linkedhu_ceng.finalVersion.config.JwtTokenUtil;
import linkedhu_ceng.finalVersion.dto.LoginDto;
import linkedhu_ceng.finalVersion.dto.SignUpDto;
import linkedhu_ceng.finalVersion.model.JwtRequest;
import linkedhu_ceng.finalVersion.model.JwtResponse;
import linkedhu_ceng.finalVersion.model.Role;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.RoleRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import linkedhu_ceng.finalVersion.service.CustomUserDetailsService;
import linkedhu_ceng.finalVersion.service.EmailService;
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

import java.io.IOException;
import java.util.*;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
public class AuthController {

    User user;

    @Autowired
    EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) throws Exception {

        if(!userRepository.existsByUserId(loginDto.getUserId())){
            return new ResponseEntity<>("User with ID: " + loginDto.getUserId() +" does not exist!", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserId(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        authenticate(loginDto.getUserId(), loginDto.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUserId());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
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

        Role role1 = roleRepository.findByName("ROLE_USER").get();
        Role role2 = roleRepository.findByName(signUpDto.getRole()).get();
        List<Role> tempList = new ArrayList<>();
        tempList.add(role1);
        tempList.add(role2);
        user.setRoles(tempList);

        userRepository.save(user);

        return new ResponseEntity<>("{\"userId\" : \""+user.getUserId()+"\"}", HttpStatus.OK);

    }

    @GetMapping("/signup/success")
    public Map<String,Object> success(){
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("id", user.getUserId());
        model.put("nameSurname", user.getName() + " " + user.getSurname());
        String nameSurname = user.getName() + " " + user.getSurname();
        emailService.sendRegistrationEmail(nameSurname, user.getUserId(), user.getEmail());
        return model;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Object createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserId(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserId());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String userId, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Successfully logged out!");
    }
}
