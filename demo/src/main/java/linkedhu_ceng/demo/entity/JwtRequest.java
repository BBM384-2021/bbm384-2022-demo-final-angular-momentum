package linkedhu_ceng.demo.entity;


import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userId;
    private String password;

    //default constructor for JSON Parsing
    public JwtRequest()
    {
    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.userId;
    }

    public void setUsername(String username) {
        this.userId = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
