package linkedhu_ceng.demo.service;

import linkedhu_ceng.demo.dto.SignUpDto;
import linkedhu_ceng.demo.entity.User;
import linkedhu_ceng.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return userRepository.findUserByUserId(userId);
    }

    public User updateUser(SignUpDto signUpDto){
        User user = getUser();

        if(signUpDto.getName() != null){
            user.setName(signUpDto.getName());
        }
        if(signUpDto.getSurname() != null){
            user.setSurname(signUpDto.getSurname());
        }
        if(signUpDto.getEmail() != null){
            user.setEmail(signUpDto.getEmail());
        }
        if(signUpDto.getPassword() != null){
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        }
        if(signUpDto.getPhoneNumber() != null){
            user.setPhoneNumber(signUpDto.getPhoneNumber());
        }

        userRepository.save(user);
        return user;
    }

    public void deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        userRepository.deleteById(userId);
    }
}
