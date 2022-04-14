package linkedhu_ceng.demo.service;


//import linkedhu_ceng.demo.config.SecurityConfig;
import linkedhu_ceng.demo.dto.SignUpDto;
import linkedhu_ceng.demo.dto.UserDto;
import linkedhu_ceng.demo.dto.PostDto;
import linkedhu_ceng.demo.entity.Post;
import linkedhu_ceng.demo.entity.User;
import linkedhu_ceng.demo.repository.PostRepository;
import linkedhu_ceng.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    //@Autowired
    //SecurityConfig securityService;

    public Post savePost(UserDto userDto, String content, String title){
        Post post = new Post();
        User user = userRepository.findUserByEmail(userDto.getEmail());
        post.setUser(user);
        post.setContent(content);
        post.setTitle(title);
        return postRepository.save(post);
    }

    public List<PostDto> getPostsOfUser(String userId){
        List<Post> postList= postRepository.findPostByUserOrderById(userRepository.findUserByUserId(userId));
        List<PostDto> postDtoList= new ArrayList<>();
        for (Post post :postList) {
            postDtoList.add(modelMapper.map(post,PostDto.class));
        }
        Collections.reverse(postDtoList);
        return postDtoList;
    }

    public List<Post> getAllPost(){
        return postRepository.findAllByOrderByIdDesc();
    }

    public Post updatePost(PostDto postDto, Integer Id){
        Post post = postRepository.findPostById(Id);

        if (postDto.getId()!=null){
            //Do Nothing
            //post.setId(post.getId());
            ;
        }
        if(postDto.getTitle() != null){
            post.setTitle(postDto.getTitle());
        }
        if(postDto.getContent() != null){
            post.setContent(postDto.getContent());
        }

        postRepository.save(post);
        return post;
    }

    public void deletePost(Integer Id){
        postRepository.deleteById(Id);
    }

}