package linkedhu_ceng.finalVersion.dto;

import java.time.ZonedDateTime;

public class CommentDto {
    private Integer Id;
    private Integer postId;
    private String text;
    private String createdById;
    private ZonedDateTime createdDate;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CommentDto [Id=" + Id + ", postId=" + postId + ", text=" + text + ", createdById=" + createdById
                + ", createdDate=" + createdDate + "]";
    }

}
