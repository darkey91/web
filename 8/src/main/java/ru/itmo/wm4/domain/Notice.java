package ru.itmo.wm4.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(indexes = @Index(columnList = "creationTime"))
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 5000)
    @Lob
    private String text;

    @CreationTimestamp
    private Date creationTime;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notice", fetch = FetchType.LAZY)
    private List<Comment> comments;

    //Поч тут eager
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "notice_tag",
                joinColumns = @JoinColumn(name = "notice_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    public List<Comment> getComments() {
        return comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
