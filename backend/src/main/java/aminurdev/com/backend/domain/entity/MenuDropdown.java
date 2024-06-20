package aminurdev.com.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_dropdowns")
public class MenuDropdown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = true)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = true)
    private Permission permission;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "route", nullable = true)
    private String route;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
