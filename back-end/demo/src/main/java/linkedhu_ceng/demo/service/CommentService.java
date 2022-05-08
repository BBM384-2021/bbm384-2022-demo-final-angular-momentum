package linkedhu_ceng.demo.service;


import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedhu_ceng.demo.entity.Post;
import linkedhu_ceng.demo.entity.Comment;
import linkedhu_ceng.demo.dto.CommentDto;
import linkedhu_ceng.demo.repository.PostRepository;
import linkedhu_ceng.demo.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private PostRepository PostRepo;

    public Comment save(CommentDto commentDto, String userId, Integer postId) {
        Comment comment = new Comment();
        //Post Post = PostRepo.getById(commentDto.getPostId());

        comment.setId(commentDto.getId());
        comment.setPost(postId);
        comment.setText(commentDto.getText());
        comment.setCreatedById(userId);

        if (comment.getId() == null)
            comment.setCreatedDate(ZonedDateTime.now());
        else
            comment.setCreatedDate(commentDto.getCreatedDate());

        return commentRepo.save(comment);
    }

    public Set<Comment> getCommentsByPostId(Integer PostId) {
        Set<Comment> comments = commentRepo.findByPostId(PostId);

        return comments;
    }

    public void delete(Integer Id) {
        commentRepo.deleteById(Id);

    }
}


