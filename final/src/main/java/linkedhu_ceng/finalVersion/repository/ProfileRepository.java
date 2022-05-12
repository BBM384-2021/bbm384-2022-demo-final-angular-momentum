package linkedhu_ceng.finalVersion.repository;

import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.Profile;
import linkedhu_ceng.finalVersion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {

    List<Profile> findProfileByUser(User user);

}
