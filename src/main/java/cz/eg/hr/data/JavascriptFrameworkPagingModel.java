package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JavascriptFrameworkPagingModel {
    @JsonProperty("frameworks")
    List<JavascriptFrameworkModel> frameworks;

    @JsonProperty("pages")
    Integer pages;
}
