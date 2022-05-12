package linkedhu_ceng.finalVersion.controller;

import linkedhu_ceng.finalVersion.dto.PostDto;
import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.dto.CommunityDto;
import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.Community;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.CommunityRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import linkedhu_ceng.finalVersion.service.CommunityService;
import linkedhu_ceng.finalVersion.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommunityRepository communityRepository;


    @PostMapping("community/add") //create a community
    public ResponseEntity<?> addCommunity(@RequestBody CommunityDto communitydto) throws NullPointerException {
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        Community createdCommunity = communityService.saveCommunity(user_v2.getUid(),communitydto.getContent(),communitydto.getTitle());
        return ResponseEntity.ok(createdCommunity);
    }

    @GetMapping("community/view") //view all communites
    public ResponseEntity<List<Community>> getAllCommunities(){
        List<Community> communityList = communityService.getAllCommunity();
        return ResponseEntity.ok(communityList);
    }

    @GetMapping("community/{Id}/members/view") //view all members of that community
    public ResponseEntity<List<String>> getCommentsByPost(@PathVariable Integer Id){ //@RequestParam Integer postId) {
        List<String> membersList = communityService.getUsersOfCommunity(Id);

        return ResponseEntity.ok(membersList);
    }

    @PutMapping("community/update/{Id}")
    public ResponseEntity<Community> updateCommunity(@PathVariable Integer Id, @RequestBody CommunityDto communityDto){
        User user = userService.getUser();
        String user_id_to_check = user.getUserId();

        Community community = communityRepository.findCommunityById(Id);
        if (community.getCreatedById().equals(user_id_to_check)){
            return ResponseEntity.ok(communityService.updateCommunity(communityDto,Id));
        }
        else{
            return null;
        }
    }

    @PutMapping("community/join/{Id}")
    public ResponseEntity<List<String>> joinCommunity(@PathVariable Integer Id){
        User user = userService.getUser();
        String user_id = user.getUserId();

        return ResponseEntity.ok(communityService.joinCommunity(user_id,Id));

    }

    @DeleteMapping("community/leave/{Id}")
    public ResponseEntity<List<String>> leaveCommunity(@PathVariable Integer Id){
        User user = userService.getUser();
        String user_id = user.getUserId();

        return ResponseEntity.ok(communityService.leaveCommunity(Id,user_id));

    }

    @DeleteMapping("/community/delete/{Id}")
    public void deleteCommunity(@PathVariable Integer Id){
        User user = userService.getUser();
        String user_id_to_check = user.getUserId();

        Community community = communityRepository.findCommunityById(Id);
        if (community.getCreatedById().equals(user_id_to_check)){
            communityService.deleteCommunity(Id);
        }
    }

}
