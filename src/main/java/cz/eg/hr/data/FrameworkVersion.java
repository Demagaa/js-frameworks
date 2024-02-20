package cz.eg.hr.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "framework_id")
    private JavascriptFramework framework;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private Date releaseDate;

    public FrameworkVersion(String version, Date releaseDate) {
        this.version = version;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "FrameworkVersion [id=" + id + ", version=" + version + ", releaseDate=" + releaseDate + "]";
    }


}

