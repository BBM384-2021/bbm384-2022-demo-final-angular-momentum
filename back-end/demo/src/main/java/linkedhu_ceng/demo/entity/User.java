package linkedhu_ceng.demo.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Random;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    private String userId = Integer.toString(10000000 + new Random().nextInt(90000000));
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
