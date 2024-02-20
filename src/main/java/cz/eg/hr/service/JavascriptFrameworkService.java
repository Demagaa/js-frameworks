package cz.eg.hr.service;

import cz.eg.hr.data.JavascriptFrameworkModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JavascriptFrameworkService {
    Page<JavascriptFrameworkModel> findAllFrameworks(Pageable pageable);
    JavascriptFrameworkModel createFramework(JavascriptFrameworkModel framework);
    JavascriptFrameworkModel updateFramework(JavascriptFrameworkModel framework, Long id);
    JavascriptFrameworkModel deleteFramework(Long id);
    Page<JavascriptFrameworkModel> searchFrameworks(Long id, String name, String latestVersion, String customVersion, Integer rating, Pageable pageable);
}
