package cz.eg.hr.service;

import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.data.JavascriptFrameworkModel;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.repository.JavascriptVersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class JavascriptFrameworkServiceImplTest {

    @Mock
    private JavascriptFrameworkRepository frameworkRepository;
    @Mock
    private JavascriptVersionRepository versionRepository;

    @InjectMocks
    private JavascriptFrameworkServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllFrameworksWithEmptyResult() {
        Pageable pageable = Pageable.unpaged();
        when(frameworkRepository.findAll(any(), eq(pageable))).thenReturn(Page.empty());

        Page<JavascriptFrameworkModel> result = service.findAllFrameworks(pageable);

        verify(frameworkRepository).findAll(any(), eq(pageable));

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllFrameworksWithOneResult() {
        JavascriptFramework framework = new JavascriptFramework("Framework", "1.0", 4);
        Page<JavascriptFramework> page = new PageImpl<>(Collections.singletonList(framework));

        Pageable pageable = Pageable.unpaged();
        when(frameworkRepository.findAll(any(), eq(pageable))).thenReturn(page);

        Page<JavascriptFrameworkModel> result = service.findAllFrameworks(pageable);

        verify(frameworkRepository).findAll(any(), eq(pageable));

        assertEquals(1, result.getContent().size());
        assertEquals("Framework", result.getContent().get(0).getName());
    }

    @Test
    public void testCreateFramework() {
        // Prepare test data
        JavascriptFrameworkModel framework = new JavascriptFrameworkModel(1L,
            "Test Framework",
            "1.0",
            new ArrayList<>(),
            5);

        service.createFramework(framework);

        verify(frameworkRepository).save(any(JavascriptFramework.class));
    }


    @Test
    public void testDeleteFramework() {
        Long id = 1L;
        JavascriptFramework deleteFramework = new JavascriptFramework();
        when(frameworkRepository.findById(id)).thenReturn(Optional.of(deleteFramework));

        JavascriptFrameworkModel deletedFramework = service.deleteFramework(id);

        verify(frameworkRepository).delete(deleteFramework);

        assertNotNull(deletedFramework);
    }

    @Test
    public void testUpdateFramework() {
        Long id = 1L;
        JavascriptFramework existingFramework = new JavascriptFramework();
        existingFramework.setId(id);
        existingFramework.setName("Existing Framework");
        existingFramework.setLatestVersion("1.0");
        existingFramework.setVersions(new ArrayList<>());
        existingFramework.setRating(4);
        when(frameworkRepository.findById(id)).thenReturn(Optional.of(existingFramework));

        JavascriptFrameworkModel updatedFrameworkModel = new JavascriptFrameworkModel();
        updatedFrameworkModel.setName("Updated Framework");
        updatedFrameworkModel.setLatestVersion("2.0");
        updatedFrameworkModel.setRating(5);

        service.updateFramework(updatedFrameworkModel, id);

        verify(frameworkRepository).save(any(JavascriptFramework.class));
    }

    @Test
    public void testSearchFrameworks() {
        List<JavascriptFramework> frameworks = new ArrayList<>();
        frameworks.add(new JavascriptFramework("Framework 1", "1.0", 4));
        frameworks.add(new JavascriptFramework("Framework 2", "2.0", 5));
        Page<JavascriptFramework> page = new PageImpl<>(frameworks);

        Pageable pageable = Pageable.unpaged();
        when(frameworkRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<JavascriptFrameworkModel> result = service.searchFrameworks(1L, "Framework 1", "1.0", "1.0", 4, pageable);

        verify(frameworkRepository).findAll(any(Specification.class), eq(pageable));

        assertEquals(2, result.getContent().size());
        assertEquals("Framework 1", result.getContent().get(0).getName());
        assertEquals("Framework 2", result.getContent().get(1).getName());
    }
}
