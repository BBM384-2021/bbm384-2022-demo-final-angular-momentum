package linkedhu_ceng.demo.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userId;
    private String password;


    public Object getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
