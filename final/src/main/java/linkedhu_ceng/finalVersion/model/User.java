package linkedhu_ceng.finalVersion.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.Random;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}
