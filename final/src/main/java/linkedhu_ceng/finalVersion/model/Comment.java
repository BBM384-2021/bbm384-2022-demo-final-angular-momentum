package linkedhu_ceng.finalVersion.model;

import java.time.ZonedDateTime;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Column(name = "comment_id")
    //@JoinColumn(name = "comment_id",referencedColumnName = "Id")
    //private Integer Id;
    //@JsonIgnore
    //@ManyToOne private Post post;

    //@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    //@JoinColumn(name = "user_id", referencedColumnName = "userId")
    //private User createdBy;


    private String createdById;
    //@JoinColumn(name = "id",referencedColumnName = "postId")
    private Integer postId;

    @Column(columnDefinition = "TEXT")
    private String text;

    private ZonedDateTime createdDate;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdBy) {
        this.createdById = createdBy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPost() {
        return postId;
    }

    public void setPost(Integer post) {
        this.postId = post;
    }
}