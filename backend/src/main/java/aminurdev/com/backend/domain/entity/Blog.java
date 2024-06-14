package aminurdev.com.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @ManyToMany
    @JoinTable(
            name = "blog_tag",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slogan", nullable = true)
    private String slogan;

    @Column(name = "slug", nullable = true)
    private String slug;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = false;

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

    public String generateSlug(String name) {
        if (StringUtils.hasText(name)) {
            // Convert name to lowercase and replace spaces with dashes
            return name.toLowerCase().replaceAll("\\s+", "-");
        }
        // Handle the case when name is empty or null
        return ""; // or throw an exception, depending on your requirements
    }
}
