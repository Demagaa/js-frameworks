package cz.eg.hr.controller;

import cz.eg.hr.conf.createJavascriptFrameworkGroup;
import cz.eg.hr.conf.updateJavascriptFrameworkGroup;
import cz.eg.hr.data.JavascriptFrameworkModel;
import cz.eg.hr.data.JavascriptFrameworkPagingModel;
import cz.eg.hr.service.JavascriptFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/frameworks")
public class JavascriptFrameworkController {

    private final JavascriptFrameworkService service;

    @Autowired
    public JavascriptFrameworkController(JavascriptFrameworkService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<JavascriptFrameworkPagingModel> getAllFrameworks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JavascriptFrameworkModel> frameworks = service.findAllFrameworks(pageable);
        return ResponseEntity.ok(new JavascriptFrameworkPagingModel(frameworks.getContent(), frameworks.getTotalPages()));
    }

    @PostMapping
    public ResponseEntity<JavascriptFrameworkModel> createFramework(@Validated(createJavascriptFrameworkGroup.class)
                                                                    @RequestBody JavascriptFrameworkModel framework) {
        JavascriptFrameworkModel createdFramework = service.createFramework(framework);
        return ResponseEntity.ok(createdFramework);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JavascriptFrameworkModel> updateFramework(@Validated(updateJavascriptFrameworkGroup.class)
                                                                    @RequestBody JavascriptFrameworkModel framework,
                                                                    @PathVariable Long id) {
        JavascriptFrameworkModel updatedFramework = service.updateFramework(framework, id);
        return ResponseEntity.ok(updatedFramework);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JavascriptFrameworkModel> deleteFramework(@PathVariable Long id) {
        JavascriptFrameworkModel framework = service.deleteFramework(id);
        return ResponseEntity.ok(framework);
    }

    @GetMapping("/search")
    public ResponseEntity<JavascriptFrameworkPagingModel> searchFrameworks(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String latestVersion,
        @RequestParam(required = false) String previousVersion,
        @RequestParam(required = false) Integer rating,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<JavascriptFrameworkModel> frameworks = service.searchFrameworks(id, name, latestVersion, previousVersion, rating, pageable);
        return ResponseEntity.ok(new JavascriptFrameworkPagingModel(frameworks.getContent(), frameworks.getTotalPages()));
    }

}
