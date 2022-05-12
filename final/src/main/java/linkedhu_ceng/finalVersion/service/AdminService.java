package linkedhu_ceng.finalVersion.service;

import linkedhu_ceng.finalVersion.model.Comment;
import linkedhu_ceng.finalVersion.model.Post;
import linkedhu_ceng.finalVersion.model.Role;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.repository.CommentRepository;
import linkedhu_ceng.finalVersion.repository.PostRepository;
import linkedhu_ceng.finalVersion.repository.RoleRepository;
import linkedhu_ceng.finalVersion.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getPendingRequests(){

        List<User> tempList = new ArrayList<>();

        for(User user : userRepository.findAll()){
            if(user.getIsRegistered().equals("false")){
                tempList.add(user);
            }
        }
        return tempList;
    }

    public void acceptRequest(String userId){

        User tempUser = userRepository.findUserByUserId(userId);
        tempUser.setIsRegistered("true");
        userRepository.save(tempUser);
    }

    public void rejectAll(){
        List<User> users = userRepository.findAll();
        for(User user : users){
            if(!user.getRoles().get(1).getName().equals("ROLE_ADMIN")){
                user.setIsRegistered("false");
                userRepository.save(user);
            }
        }
    }

    public void updateUserRole(String userId, String role){
        User tempUser = userRepository.findUserByUserId(userId);
        Role newRole = roleRepository.findByName(role).get();
        Role role1 = roleRepository.findByName("ROLE_USER").get();
        List<Role> tempList = new ArrayList<>();
        tempList.add(role1);
        tempList.add(newRole);
        tempUser.setRoles(tempList);
        userRepository.save(tempUser);
    }

    public void deleteUser(String userId){

        List<Post> posts = postRepository.findPostByUserOrderById(userRepository.findUserByUserId(userId));
        for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext();) {
            Post post = iterator.next();
            post.setUser(null);

            try {
                Set<Comment> comments = commentRepository.findByPostId(post.getId());
                for (Iterator<Comment> iterator_comment = comments.iterator(); iterator_comment.hasNext(); ) {
                    Comment comment = iterator_comment.next();
                    comment.setPost(null);
                    commentRepository.deleteById(comment.getId());
                    iterator_comment.remove();
                }
            }

            finally {
                postRepository.deleteById(post.getId());
                iterator.remove(); //remove the child first
            }

        }

        List<Comment> comments = commentRepository.findCommentByCreatedByIdOrderById(userId);
        for (Iterator<Comment> iterator = comments.iterator(); iterator.hasNext();) {
            Comment comment= iterator.next();
            comment.setPost(null);
            commentRepository.deleteById(comment.getId());
            iterator.remove();
        }
        userRepository.deleteById(userId);
    }

    public List<User> getAllUser() {

        return userRepository.findAll();
    }

    public ByteArrayInputStream export(){

        List<User> users = userRepository.findAll();

        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("Users");

            Row row = sheet.createRow(0);

            // Define header cell style
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Creating header cells
            Cell cell = row.createCell(0);
            cell.setCellValue("User ID");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Name");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Surname");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Phone Number");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Role");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each contact
            for(int i = 0; i < users.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(users.get(i).getUserId());
                dataRow.createCell(1).setCellValue(users.get(i).getName());
                dataRow.createCell(2).setCellValue(users.get(i).getSurname());
                dataRow.createCell(3).setCellValue(users.get(i).getEmail());
                dataRow.createCell(4).setCellValue(users.get(i).getPhoneNumber());
                dataRow.createCell(5).setCellValue(userService.convertRole(users.get(i).getRoles().get(1).getName()));
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            logger.error("Error during export Excel file", ex);
            return null;
        }
    }
}
