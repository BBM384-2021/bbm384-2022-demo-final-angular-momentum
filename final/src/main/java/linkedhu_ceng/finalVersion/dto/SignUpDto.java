package linkedhu_ceng.finalVersion.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}
