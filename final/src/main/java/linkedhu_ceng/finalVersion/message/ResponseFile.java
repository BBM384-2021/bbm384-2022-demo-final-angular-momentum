package linkedhu_ceng.finalVersion.message;

public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
    private Integer postId;
    private String userId;

    public ResponseFile(String name, String url, String type, long size, Integer postId, String userId) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.postId = postId;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
