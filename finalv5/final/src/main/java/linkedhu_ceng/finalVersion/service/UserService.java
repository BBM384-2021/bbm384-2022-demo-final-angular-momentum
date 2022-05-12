package linkedhu_ceng.finalVersion.service;

import linkedhu_ceng.finalVersion.dto.SignUpDto;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.PostRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        return optionalUser.get();
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

        List<Post> posts = postRepository.findPostByUserOrderById(userRepository.findUserByUserId(userId));
        for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext();) {
            Post post = iterator.next();
            post.setUser(null);
            iterator.remove(); //remove the child first
        }
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}