package linkedhu_ceng.finalVersion.service;


//import SecurityConfig;
import linkedhu_ceng.finalVersion.dto.CommunityDto;
import linkedhu_ceng.finalVersion.model.Community;
import linkedhu_ceng.finalVersion.repository.CommunityRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class CommunityService {

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public Community saveCommunity(String uid, String content, String title){
        List<String> members = new ArrayList<String>();
        members.add(uid);
        Community community = new Community();
        community.setUserId(members);
        community.setCreatedById(uid);
        community.setContent(content);
        community.setTitle(title);

        return communityRepository.save(community);
    }

    public List<Community> getAllCommunity(){
        return communityRepository.findAllByOrderByIdDesc();
    }

    public Community updateCommunity(CommunityDto communityDto, Integer Id){
        Community community = communityRepository.findCommunityById(Id);

        if (communityDto.getId()!=null){
            //Do Nothing
            ;
            community.setUserId(communityDto.getUserId());
        }
        if(communityDto.getTitle() != null){
            community.setTitle(communityDto.getTitle());
        }
        if(communityDto.getContent() != null){
            community.setContent(communityDto.getContent());
        }

        communityRepository.save(community);
        return community;
    }

    public List<String> joinCommunity(String uid,Integer Id){
        Community community = communityRepository.findCommunityById(Id);
        List <String> members = community.getUserId();
        members.add(uid);
        community.setUserId(members);
        communityRepository.save(community);
        return community.getUserId();
    }
    
    public List<String> leaveCommunity (Integer Id, String uid){
        Community community = communityRepository.findCommunityById(Id);
        List<String> members = this.getUsersOfCommunity(Id);
        members.remove(uid);
        community.setUserId(members);
        communityRepository.save(community);
        return community.getUserId();
    }
    
    public List<String> getUsersOfCommunity(Integer Id){
        Community community = communityRepository.findCommunityById(Id);

        return community.getUserId();
    }

    public void deleteCommunity(Integer Id){
        communityRepository.deleteById(Id);
    }
}
