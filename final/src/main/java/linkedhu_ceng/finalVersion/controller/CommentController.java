package linkedhu_ceng.finalVersion.controller;


import java.util.Set;

import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.repository.CommentRepository;
import linkedhu_ceng.finalVersion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.dto.CommentDto;
import linkedhu_ceng.finalVersion.service.CommentService;

@RestController
@RequestMapping("/api/post/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://assignments.cod3rscampus.com"}, allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @PostMapping("{postId}/comment/add")
    public ResponseEntity<Comment> createComment (@PathVariable Integer postId, @RequestBody CommentDto commentDto) {
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        String userId = user_v2.getUid();
        Comment comment = commentService.save(commentDto, userId, postId);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("{postId}/comment/update/{Id}")
    public ResponseEntity<Comment> updateComment (@PathVariable Integer postId, @PathVariable Integer Id, @RequestBody CommentDto commentDto) {
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        String userId = user_v2.getUid();

        Comment comment = commentRepository.findCommentById(Id);
        if(comment.getCreatedById().equals(userId)){
            Comment comment_v2 = commentService.save(commentDto, userId, postId);

            return ResponseEntity.ok(comment_v2);
        }

        else{
            return null;
        }

    }

    @GetMapping("{postId}/comment/view")
    public ResponseEntity<Set<Comment>> getCommentsByPost(@PathVariable Integer postId){ //@RequestParam Integer postId) {
        Set<Comment> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("{postId}/comment/delete/{Id}")
    public ResponseEntity<?> deleteComment (@PathVariable Integer postId, @PathVariable Integer Id) {

        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        String userId = user_v2.getUid();

        Comment comment = commentRepository.findCommentById(Id);
        if(comment.getCreatedById().equals(userId)){
            try {
                commentService.delete(Id);
                return ResponseEntity.ok("Comment deleted");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        else{
            return null;
        }


    }
}
