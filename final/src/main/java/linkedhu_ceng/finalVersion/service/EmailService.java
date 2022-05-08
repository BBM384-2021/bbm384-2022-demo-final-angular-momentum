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

    public void sendEmail(String role, String title, String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("linkedhuceng.assist@gmail.com");

        List<User> temp_list = userRepository.findAll();
        for (User user : temp_list) {
            for (int k = 0; k < user.getRoles().size(); k++) {
                if (user.getRoles().get(k).getName().contains(role)) {
                    message.setTo(user.getEmail());
                    message.setSubject(title);
                    message.setText(body);
                    mailSender.send(message);
                }
            }
        }
    }
}
