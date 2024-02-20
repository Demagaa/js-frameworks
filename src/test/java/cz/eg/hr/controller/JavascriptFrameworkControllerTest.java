package cz.eg.hr.controller;

import cz.eg.hr.data.JavascriptFrameworkModel;
import cz.eg.hr.data.JavascriptFrameworkPagingModel;
import cz.eg.hr.service.JavascriptFrameworkService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JavascriptFrameworkControllerTest {

    @Mock
    private JavascriptFrameworkService service;

    @InjectMocks
    private JavascriptFrameworkController controller;

    @Test
    public void testGetAllFrameworks() {
        Page<JavascriptFrameworkModel> page = new PageImpl<>(Collections.emptyList());
        when(service.findAllFrameworks(any())).thenReturn(page);

        ResponseEntity<JavascriptFrameworkPagingModel> response = controller.getAllFrameworks(0, 10);

        verify(service).findAllFrameworks(any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateFramework() {
        JavascriptFrameworkModel framework = new JavascriptFrameworkModel();
        when(service.createFramework(any())).thenReturn(framework);

        ResponseEntity<JavascriptFrameworkModel> response = controller.createFramework(framework);

        verify(service).createFramework(any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(framework, response.getBody());
    }

    @Test
    public void testUpdateFramework() {
        Long id = 1L;
        JavascriptFrameworkModel framework = new JavascriptFrameworkModel();
        when(service.updateFramework(any(), eq(id))).thenReturn(framework);

        ResponseEntity<JavascriptFrameworkModel> response = controller.updateFramework(framework, id);

        verify(service).updateFramework(any(), eq(id));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(framework, response.getBody());
    }

    @Test
    public void testDeleteFramework() {
        Long id = 1L;
        JavascriptFrameworkModel framework = new JavascriptFrameworkModel();
        when(service.deleteFramework(eq(id))).thenReturn(framework);

        ResponseEntity<JavascriptFrameworkModel> response = controller.deleteFramework(id);

        verify(service).deleteFramework(eq(id));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(framework, response.getBody());
    }


    @Test
    public void testEmptySearchFrameworks() {
        List<JavascriptFrameworkModel> result = Collections.emptyList();

        Page<JavascriptFrameworkModel> page = new PageImpl<>(result);

        when(service.searchFrameworks(anyLong(), anyString(), anyString(), anyString(), anyInt(), any(Pageable.class))).thenReturn(page);

        ResponseEntity<JavascriptFrameworkPagingModel> response = controller.searchFrameworks(5L,
            "null",
            "null",
            "null",
            4,
            0,
            10);

        verify(service).searchFrameworks(anyLong(), anyString(), anyString(), anyString(), anyInt(), any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<JavascriptFrameworkModel> frameworks = response.getBody().getFrameworks();

        assertNotNull(frameworks);
        assertTrue(frameworks.isEmpty());
    }
    @Test
    public void testSearchFrameworksWithResult() {
        JavascriptFrameworkModel framework = new JavascriptFrameworkModel();
        framework.setId(null);
        framework.setName("null");
        framework.setLatestVersion("null");
        framework.setVersions(Collections.emptyList());
        framework.setRating(null);

        List<JavascriptFrameworkModel> result = Collections.singletonList(framework);

        Page<JavascriptFrameworkModel> page = new PageImpl<>(result);

        when(service.searchFrameworks(anyLong(), anyString(), anyString(), anyString(), anyInt(), any(Pageable.class))).thenReturn(page);

        ResponseEntity<JavascriptFrameworkPagingModel> response = controller.searchFrameworks(5L, "null", "null", "null", 4, 0, 10);

        verify(service).searchFrameworks(anyLong(), anyString(), anyString(), anyString(), anyInt(), any());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JavascriptFrameworkPagingModel responseBody = response.getBody();
        assertNotNull(responseBody);

        List<JavascriptFrameworkModel> frameworks = responseBody.getFrameworks();

        assertNotNull(frameworks);
        assertEquals(1, frameworks.size());
        assertEquals(framework, frameworks.get(0));
    }

}
