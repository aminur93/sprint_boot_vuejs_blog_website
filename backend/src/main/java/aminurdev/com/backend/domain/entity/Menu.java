package aminurdev.com.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = true)
    private Permission permission;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "route", nullable = true)
    private String route;

    @Column(name = "header_menu", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean headerMenu = false;

    @Column(name = "sidebar_menu", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean sidebarMenu = false;

    @Column(name = "dropdown", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean dropdown = false;

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

    @OneToMany(mappedBy = "menu")
    @JsonIncludeProperties({"id","permission", "title", "icon", "route", "created_at", "updated_at"})
    @JsonManagedReference
    @JsonBackReference
    private List<MenuDropdown> menuDropdowns;
}
