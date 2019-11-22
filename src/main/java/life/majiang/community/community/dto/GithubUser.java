package life.majiang.community.community.dto;

public class GithubUser {

    private String name;
    private Long id; //这个是用来存放用户人数的？人数可能多 用long
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
