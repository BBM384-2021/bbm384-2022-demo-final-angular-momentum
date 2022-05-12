package linkedhu_ceng.finalVersion.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import linkedhu_ceng.finalVersion.model.FileDB;
import linkedhu_ceng.finalVersion.repository.FileDBRepository;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file,Integer postId, String userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        FileDB.setPostId(postId);
        FileDB.setUserId(userId);
        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public Stream<FileDB> getFilesByPostId(Integer postId) {
        return fileDBRepository.findByPostId(postId).stream();
    }
}

