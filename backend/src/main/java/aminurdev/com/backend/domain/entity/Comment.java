package aminurdev.com.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    @JsonIncludeProperties({"id", "title", "slogan", "slug", "created_at", "updated_at"})
    @JsonManagedReference
    @JsonBackReference
    private Blog blog;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "comment")
    @JsonIncludeProperties({"id", "name", "reply", "created_at", "updated_at"})
    @JsonManagedReference
    @JsonBackReference
    private List<Reply> replys;
}
