package linkedhu_ceng.finalVersion.service;


import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.dto.CommentDto;
import linkedhu_ceng.finalVersion.repository.PostRepository;
import linkedhu_ceng.finalVersion.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private PostRepository PostRepo;

    public Comment save(CommentDto commentDto, String userId, String nameSurname, Integer postId) {
        Comment comment = new Comment();
        //Post Post = PostRepo.getById(commentDto.getPostId());

        comment.setId(commentDto.getId());
        comment.setPost(postId);
        comment.setText(commentDto.getText());
        comment.setCreatedById(userId);
        comment.setNameSurname(nameSurname);

        comment.setCreatedDate(ZonedDateTime.now());
        //if (comment.getId() == null)
        //comment.setCreatedDate(ZonedDateTime.now());
        //else
        //comment.setCreatedDate(commentDto.getCreatedDate());

        return commentRepo.save(comment);
    }

    public Comment update(CommentDto commentDto, Integer id){

        Comment tempComment = commentRepo.findCommentById(id);
        tempComment.setText(commentDto.getText());
        return commentRepo.save(tempComment);
    }

    public Set<Comment> getCommentsByPostId(Integer PostId) {
        Set<Comment> comments = commentRepo.findByPostId(PostId);

        return comments;
    }

    public void delete(Integer Id) {
        commentRepo.deleteById(Id);

    }
}
