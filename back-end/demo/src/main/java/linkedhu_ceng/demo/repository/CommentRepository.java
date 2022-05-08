package linkedhu_ceng.demo.repository;


import java.util.Set;

import linkedhu_ceng.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import linkedhu_ceng.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

    @Query("select c from Comment c "
            + " where c.postId = :postId")
    Set<Comment> findByPostId(Integer postId);

    Comment findCommentById(Integer Id);

}
