package linkedhu_ceng.demo.repository;

import linkedhu_ceng.demo.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserIdOrEmail(String userId, String email);
    Optional<User> findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
    List<User> findAll();
    User findUserByUserId(String userId);
    User findUserByEmail(String email);
}