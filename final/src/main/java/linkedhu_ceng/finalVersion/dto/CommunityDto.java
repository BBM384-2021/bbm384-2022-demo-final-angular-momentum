package linkedhu_ceng.finalVersion.dto;

import javax.persistence.Column;
import java.util.List;

public class CommunityDto {
    private Integer id;
    private List<String> userId;
    private String title;
    private String content;
    private String createdById;

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Integer getId() {
        return id;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
