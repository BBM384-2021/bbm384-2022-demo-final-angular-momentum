package linkedhu_ceng.finalVersion.controller;

import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.model.Profile;
import linkedhu_ceng.finalVersion.service.ProfileService;
import linkedhu_ceng.finalVersion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import linkedhu_ceng.finalVersion.dto.ProfileDto;

import java.util.Set;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @PostMapping("profile/edit")
    public ResponseEntity<?> addPost(@RequestBody ProfileDto profileDto) throws NullPointerException {
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        Profile newProfile = profileService.editProfile(user_v2,profileDto.getName(), profileDto.getSurname(),profileDto.getEmail(), profileDto.getJob(), profileDto.getCompany(), profileDto.getBio());
        return ResponseEntity.ok(newProfile);
    }

    @GetMapping("{userId}/profile/search")
    public ResponseEntity<?> search(@PathVariable Integer userId){
        Profile profile = profileService.getProfileOfUser(userId);
        return ResponseEntity.ok(profile);
    }
}
