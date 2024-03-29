package linkedhu_ceng.finalVersion.service;


//import SecurityConfig;
import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.dto.PostDto;
import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.CommentRepository;
import linkedhu_ceng.finalVersion.repository.PostRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    //@Autowired
    //SecurityConfig securityService;

    public Post savePost(UserDto userDto, String type, String content, String title){
        Post post = new Post();
        User user = userRepository.findUserByEmail(userDto.getEmail());
        post.setUser(user);
        post.setPostType(type);
        post.setContent(content);
        post.setTitle(title);
        post.setCreatedDate(ZonedDateTime.now());
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
            post.setCreatedDate(ZonedDateTime.now());
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
        try {
            Set<Comment> comments = commentRepository.findByPostId(Id);
            for (Iterator<Comment> iterator_comment = comments.iterator(); iterator_comment.hasNext(); ) {
                Comment comment = iterator_comment.next();
                comment.setPost(null);
                commentRepository.deleteById(comment.getId());
                iterator_comment.remove();
            }
        }
        finally{
            postRepository.deleteById(Id);}
    }

    public List<Post> searchPost(String text){
        List<Post> posts = postRepository.findAll();
        List<Post> returnPost = new ArrayList<>();
        for(Post post : posts){
            if(post.getTitle().toLowerCase().contains(text.toLowerCase()) || post.getContent().toLowerCase().contains(text.toLowerCase())){
                returnPost.add(post);
            }
        }
        return returnPost;
    }
}
