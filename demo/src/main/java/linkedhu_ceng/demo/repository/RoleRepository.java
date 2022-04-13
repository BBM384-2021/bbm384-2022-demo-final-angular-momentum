package linkedhu_ceng.demo.repository;

import linkedhu_ceng.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//import linkedhu_ceng.demo.entity.ERole;
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
