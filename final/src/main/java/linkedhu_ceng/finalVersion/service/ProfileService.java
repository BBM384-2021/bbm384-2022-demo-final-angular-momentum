package linkedhu_ceng.finalVersion.service;
import linkedhu_ceng.finalVersion.model.Profile;
import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import linkedhu_ceng.finalVersion.repository.ProfileRepository;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository ;

    public Profile editProfile(UserDto userDto, String name, String surname, String email,String job,String company, String bio){
        Profile profile = new Profile();
        User user = userRepository.findUserByEmail(userDto.getEmail());
        profile.setUser(user);
        profile.setName(name);
        profile.setSurname(surname);
        profile.setEmail(email);
        profile.setCompany(company);
        profile.setJob(job);
        profile.setBio(bio);
        return profileRepository.save(profile);
    }

    public Profile getProfileOfUser(Integer userId){

        User user = userRepository.findUserByUserId(userId.toString());

        if (user!=null) {

            Profile profile= profileRepository.findProfileByUser(user).get(0);

            return profile;

        }

        return null;
    }
}
