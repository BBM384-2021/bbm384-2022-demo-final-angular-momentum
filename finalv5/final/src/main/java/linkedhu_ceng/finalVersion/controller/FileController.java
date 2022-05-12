package linkedhu_ceng.finalVersion.controller;

import java.util.List;
import java.util.stream.Collectors;

import linkedhu_ceng.finalVersion.dto.UserDto;
import linkedhu_ceng.finalVersion.model.User;
import linkedhu_ceng.finalVersion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import linkedhu_ceng.finalVersion.service.FileStorageService;
import linkedhu_ceng.finalVersion.message.ResponseFile;
import linkedhu_ceng.finalVersion.message.ResponseMessage;
import linkedhu_ceng.finalVersion.model.FileDB;

@Controller
@CrossOrigin("http://localhost:8081")
//@RequestMapping("/api/post/")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    UserService userService;

//    @PostMapping("/upload")
    @PostMapping("/api/post/{postId}/file/add")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Integer postId, @RequestParam("file") MultipartFile file) {
        User user = userService.getUser();
        UserDto user_v2 = new UserDto(user);
        String userId = user_v2.getUid();
        String message = "";
        try {
            storageService.store(file,postId,userId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/api/post/{postId}/file/view")
//    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable Integer postId) {
        List<ResponseFile> files = storageService.getFilesByPostId(postId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getPostId(),
                    dbFile.getUserId());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}

