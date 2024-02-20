package cz.eg.hr.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkVersionModel {

    private Long id;

    private String version;

    private Date releaseDate;

    @Override
    public String toString() {
        return "FrameworkVersionModel{" +
            "id=" + id +
            ", version='" + version + '\'' +
            ", releaseDate=" + releaseDate +
            '}';
    }
}

