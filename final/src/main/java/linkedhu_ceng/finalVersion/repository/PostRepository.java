package linkedhu_ceng.finalVersion.repository;


import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findPostByUserOrderById(User user);

    List<Post> findAllByOrderByIdDesc();

    Post findPostById(Integer Id);
}
