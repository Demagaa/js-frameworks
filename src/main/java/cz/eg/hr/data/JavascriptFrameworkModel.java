package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.eg.hr.conf.createJavascriptFrameworkGroup;
import cz.eg.hr.conf.updateJavascriptFrameworkGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "latestVersion", "versions", "rating"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JavascriptFrameworkModel {

    private static final String VERSION_PATTERN = "^(\\d+\\.)?(\\d+\\.)?(\\*|\\d+)$";

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank(groups = createJavascriptFrameworkGroup.class, message = "Non-empty name is mandatory")
    @Length(max = 30)
    private String name;

    @JsonProperty("latestVersion")
    @NotNull(message = "Latest version is mandatory", groups = createJavascriptFrameworkGroup.class)
    @Pattern(groups = {createJavascriptFrameworkGroup.class, updateJavascriptFrameworkGroup.class}, regexp = VERSION_PATTERN, message = "Invalid version format")
    private String latestVersion;

    @JsonProperty("versions")
    @Valid
    private List<FrameworkVersionModel> versions = new ArrayList<>();

    @JsonProperty("rating")
    @Min(groups = {createJavascriptFrameworkGroup.class, updateJavascriptFrameworkGroup.class}, value = 1, message = "Rating must be at least 1")
    @Max(groups = {createJavascriptFrameworkGroup.class, updateJavascriptFrameworkGroup.class}, value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Override
    public String toString() {
        return "JavascriptFrameworkModel{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", latestVersion='" + latestVersion + '\'' +
            ", versions=" + versions +
            ", rating=" + rating +
            '}';
    }
}

