package linkedhu_ceng.finalVersion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import linkedhu_ceng.finalVersion.model.FileDB;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    List<FileDB> findByPostId(Integer postId);
}

