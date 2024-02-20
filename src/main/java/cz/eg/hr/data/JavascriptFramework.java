package cz.eg.hr.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JavascriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column
    private String latestVersion;

    @OneToMany(mappedBy = "framework", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FrameworkVersion> versions = new ArrayList<>();

    @Column
    private Integer rating;

    public JavascriptFramework(String name, String latestVersion, Integer rating) {
        this.name = name;
        this.latestVersion = latestVersion;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
    }
}
