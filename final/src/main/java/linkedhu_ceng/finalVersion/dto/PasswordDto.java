package linkedhu_ceng.finalVersion.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmation;
}
