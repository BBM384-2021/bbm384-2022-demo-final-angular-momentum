package linkedhu_ceng.finalVersion.service;

import linkedhu_ceng.finalVersion.dto.PasswordDto;
import linkedhu_ceng.finalVersion.dto.SignUpDto;
import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.CommentRepository;
import linkedhu_ceng.finalVersion.repository.PostRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

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

    public void updatePassword(PasswordDto passwordDto){
        User user = getUser();
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Post> posts = postRepository.findPostByUserOrderById(userRepository.findUserByUserId(userId));
        for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext();) {
            Post post = iterator.next();
            post.setUser(null);

            try {
                Set<Comment> comments = commentRepository.findByPostId(post.getId());
                for (Iterator<Comment> iterator_comment = comments.iterator(); iterator_comment.hasNext(); ) {
                    Comment comment = iterator_comment.next();
                    comment.setPost(null);
                    commentRepository.deleteById(comment.getId());
                    iterator_comment.remove();
                }
            }

            finally {
                postRepository.deleteById(post.getId());
                iterator.remove(); //remove the child first
            }

        }

        List<Comment> comments = commentRepository.findCommentByCreatedByIdOrderById(userId);
        for (Iterator<Comment> iterator = comments.iterator(); iterator.hasNext();) {
            Comment comment= iterator.next();
            comment.setPost(null);
            commentRepository.deleteById(comment.getId());
            iterator.remove();
        }
        userRepository.deleteById(userId);
    }

    public String convertRole(String role){
        if(Objects.equals(role, "ROLE_STUDENT")){
            return "Student";
        }

        else if(Objects.equals(role, "ROLE_ACADEMICIAN")){
            return "Academician";
        }

        else if(Objects.equals(role, "ROLE_GRADUATE")){
            return "Graduate";
        }

        else if(Objects.equals(role, "ROLE_STUDENT_REP")){
            return "Student Representative";
        }

        else if(Objects.equals(role, "ROLE_ADMIN")){
            return "Admin";
        }

        else{
            return "";
        }
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
