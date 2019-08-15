package xyz.majorkevin.bbs.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "agree_amount")
    private int agreeAmount;

    @Column(name = "oppose_amount")
    private int opposeAmount;

    @Column(name = "comment_time")
    private Date commentTime;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    public Comment() {
    }

    public Comment(String content, int agreeAmount, int opposeAmount) {
        this.content = content;
        this.agreeAmount = agreeAmount;
        this.opposeAmount = opposeAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgreeAmount() {
        return agreeAmount;
    }

    public void setAgreeAmount(int agreeAmount) {
        this.agreeAmount = agreeAmount;
    }

    public int getOpposeAmount() {
        return opposeAmount;
    }

    public void setOpposeAmount(int opposeAmount) {
        this.opposeAmount = opposeAmount;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
