package linkedhu_ceng.finalVersion.controller;

import linkedhu_ceng.finalVersion.dto.EmailDto;
import linkedhu_ceng.finalVersion.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto){
        emailService.sendEmail(emailDto.getRole(), emailDto.getTitle(), emailDto.getBody());
        return ResponseEntity.ok("Email successfully sent! ");
    }
}
