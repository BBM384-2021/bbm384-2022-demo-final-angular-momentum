package linkedhu_ceng.finalVersion.service;

import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public void sendEmail(String[] roleList, String title, String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("linkedhuceng.assist@gmail.com");

        User currentUser = userService.getUser();
        String currentUserNameSurname = currentUser.getName() + " " + currentUser.getSurname();
        String currentUserRole = userService.convertRole(currentUser.getRoles().get(1).getName());

        List<User> temp_list = userRepository.findAll();
        for(String role : roleList){
            for (User user : temp_list) {
                for (int k = 0; k < user.getRoles().size(); k++) {
                    if (user.getRoles().get(k).getName().contains(role)) {
                        message.setTo(user.getEmail());
                        message.setSubject(title);
                        message.setText("-- " + currentUserRole + " " + currentUserNameSurname + " sent email -- \n\n" +body);
                        message.setReplyTo(currentUser.getEmail());
                        mailSender.send(message);
                    }
                }
            }
        }
    }


    public void sendRegistrationEmail(String userId){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("linkedhuceng.assist@gmail.com");

        User newUser = userRepository.findUserByUserId(userId);
        String userNameSurname = newUser.getName() + " " + newUser.getSurname();

        message.setTo(newUser.getEmail());
        message.setSubject("Welcome to LinkedHU_CENG!");
        message.setText("Dear " + userNameSurname + ",\n" +
        "Welcome to LinkedHU_CENG. You registered successfully!\n"
                + "Your User ID is " + userId + "\n" +
                "You can login with your User ID and password");
        mailSender.send(message);
    }
}
