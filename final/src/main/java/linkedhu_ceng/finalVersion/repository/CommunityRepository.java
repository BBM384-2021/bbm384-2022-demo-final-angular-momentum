package linkedhu_ceng.finalVersion.repository;

import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.Community;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer>{

    List<Community> findAllByOrderByIdDesc();

    Community findCommunityById(Integer Id);

}
