package linkedhu_ceng.finalVersion.controller;


import linkedhu_ceng.finalVersion.dto.RoleDto;
import linkedhu_ceng.finalVersion.service.AdminService;
import linkedhu_ceng.finalVersion.service.EmailService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    EmailService emailService;

    @GetMapping("/admin/request/get")
    public ResponseEntity<?> getRequests(){
        return ResponseEntity.ok(adminService.getPendingRequests());
    }

    @PutMapping("admin/request/accept/{userId}")
    public void acceptRequest(@PathVariable String userId){
        adminService.acceptRequest(userId);
        emailService.sendRegistrationEmail(userId);
    }

    @DeleteMapping("admin/request/reject/{userId}")
    public void rejectRequest(@PathVariable String userId){
        adminService.deleteUser(userId);
    }

    @GetMapping("/admin/user/all")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(adminService.getAllUser());
    }

    @PutMapping("admin/user/update/role/{userId}")
    public void updateUserRole(@PathVariable String userId, @RequestBody RoleDto roleDto){
        adminService.updateUserRole(userId, roleDto.getRole());
    }

    @DeleteMapping("admin/user/delete/{userId}")
    public void deleteUser(@PathVariable String userId){
        adminService.deleteUser(userId);
    }

    @GetMapping("/admin/user/download")
    public void downloadUserInfo(HttpServletResponse response) throws IOException {
        ByteArrayInputStream byteArrayInputStream = adminService.export();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }

    @PutMapping("/admin/umut")
    public void forUmut(){
        adminService.rejectAll();
    }
}
