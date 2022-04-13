package linkedhu_ceng.demo.controller;

//import linkedhu_ceng.demo.config.SecurityConfig;
import linkedhu_ceng.demo.dto.UserDto;
import linkedhu_ceng.demo.dto.PostDto;
import linkedhu_ceng.demo.entity.Post;
import linkedhu_ceng.demo.entity.User;
import linkedhu_ceng.demo.repository.UserRepository;
import linkedhu_ceng.demo.service.PostService;
import linkedhu_ceng.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;


    //@Autowired
    //SecurityConfig securityService;
    //public Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    @PostMapping("post/add")
    public ResponseEntity<?> addPost(@RequestBody Post post) throws NullPointerException {
        //UserDto user = securityService.getUser();
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        Post savedPost = postService.savePost(user_v2,post.getContent(),post.getTitle());
        return ResponseEntity.created(URI.create("/private/mypost")).body(savedPost);
    }

    @GetMapping("mypost/view")
    public ResponseEntity<?> myPosts() throws NullPointerException {
        //User user=userService.getUser(securityService.getUser().getEmail());
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        List<PostDto> postList = postService.getPostsOfUser(user_v2.getUid());
        return ResponseEntity.ok(postList);
    }

    @GetMapping("post/view")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> postList = postService.getAllPost();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("{userId}/post/view")
    public ResponseEntity<?> getPostofUser(@PathVariable String userId){
        List<PostDto> postList = postService.getPostsOfUser(userId);
        return ResponseEntity.ok(postList);
    }

}
