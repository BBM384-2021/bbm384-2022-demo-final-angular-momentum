package linkedhu_ceng.demo.repository;

import linkedhu_ceng.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserIdOrEmail(String userId, String email);
    Optional<User> findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
}