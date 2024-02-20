package cz.eg.hr.service;

import cz.eg.hr.data.FrameworkVersion;
import cz.eg.hr.data.FrameworkVersionModel;
import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.data.JavascriptFrameworkModel;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.repository.JavascriptVersionRepository;
import cz.eg.hr.repository.Specifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService {
    private final JavascriptFrameworkRepository javascriptFrameworkRepository;
    private final JavascriptVersionRepository versionRepository;


    public JavascriptFrameworkServiceImpl(JavascriptFrameworkRepository javascriptFrameworkRepository,
                                          JavascriptVersionRepository versionRepository) {
        this.javascriptFrameworkRepository = javascriptFrameworkRepository;
        this.versionRepository = versionRepository;
    }

    @Override
    public Page<JavascriptFrameworkModel> findAllFrameworks(Pageable pageable) {
        Page<JavascriptFramework> frameworks = javascriptFrameworkRepository.findAll(Specifications.hasId(null), pageable);
        return frameworks.map(this::convertEntityToModel);
    }

    @Override
    public JavascriptFrameworkModel createFramework(JavascriptFrameworkModel framework) {
        JavascriptFramework javascriptFramework = convertFrameworkModelToEntity(framework);
        javascriptFrameworkRepository.save(javascriptFramework);

        if (!framework.getVersions().isEmpty()) {
            List<FrameworkVersion> frameworkVersions = convertModelToEntityList(framework.getVersions(), javascriptFramework);
            javascriptFramework.setVersions(frameworkVersions);

            javascriptFramework = javascriptFrameworkRepository.save(javascriptFramework);
        }

        framework.setId(javascriptFramework.getId());
        return framework;
    }

    @Override
    public JavascriptFrameworkModel updateFramework(JavascriptFrameworkModel framework, Long id) throws NoSuchElementException {
        JavascriptFramework update = javascriptFrameworkRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("No framework ID found"));

        if (framework.getName() != null) {
            update.setName(framework.getName());
        }
        if (framework.getLatestVersion() != null) {
            List<FrameworkVersion> versions = update.getVersions();

            FrameworkVersion version = new FrameworkVersion(update.getLatestVersion(), new Date());
            version.setFramework(update);
            versionRepository.save(version);

            versions.add(version);
            update.setLatestVersion(framework.getLatestVersion());
        }
        if (framework.getRating() != null) {
            update.setRating(framework.getRating());
        }

        if (framework.getVersions().isEmpty()) {
            List<FrameworkVersion> frameworkVersions = convertModelToEntityList(framework.getVersions(), update);
            if (!update.getVersions().isEmpty()) {
                update.getVersions().addAll(frameworkVersions);
            } else {
                update.setVersions(frameworkVersions);
            }
        }

        javascriptFrameworkRepository.save(update);
        return new JavascriptFrameworkModel(update.getId(),
            update.getName(),
            update.getLatestVersion(),
            convertEntityToModelList(update.getVersions()),
            update.getRating());
    }

    @Override
    public JavascriptFrameworkModel deleteFramework(Long id) {
        JavascriptFramework delete = javascriptFrameworkRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ID not found"));
        javascriptFrameworkRepository.delete(delete);
        return convertEntityToModel(delete);
    }

    @Override
    public Page<JavascriptFrameworkModel> searchFrameworks(Long id, String name, String latestVersion, String version, Integer rating, Pageable pageable) {
        Specification<JavascriptFramework> combinedSpecification = getJavascriptFrameworkSpecification(id, name, latestVersion, version, rating);
        Page<JavascriptFramework> result = javascriptFrameworkRepository.findAll(combinedSpecification, pageable);
        return result.map(this::convertEntityToModel);
    }

    private Specification<JavascriptFramework> getJavascriptFrameworkSpecification(Long id, String name, String latestVersion, String version, Integer rating) {
        Specification<JavascriptFramework> specificationId = Specifications.hasId(id);
        Specification<JavascriptFramework> specificationName = Specifications.hasName(name);
        Specification<JavascriptFramework> specificationVersion = Specifications.hasLatestVersion(latestVersion);
        Specification<JavascriptFramework> specificationRating = Specifications.hasRating(rating);
        Specification<JavascriptFramework> specificationVersions = Specifications.hasVersion(version);

        return specificationId
            .and(specificationName)
            .and(specificationVersion)
            .and(specificationRating)
            .and(specificationVersions);
    }

    private JavascriptFrameworkModel convertEntityToModel(JavascriptFramework framework) {
        return new JavascriptFrameworkModel(
            framework.getId(),
            framework.getName(),
            framework.getLatestVersion(),
            convertEntityToModelList(framework.getVersions()),
            framework.getRating()
        );
    }

    private JavascriptFramework convertFrameworkModelToEntity(JavascriptFrameworkModel frameworkModel) {
        JavascriptFramework javascriptFramework = new JavascriptFramework();
        javascriptFramework.setId(frameworkModel.getId());
        javascriptFramework.setName(frameworkModel.getName());
        javascriptFramework.setLatestVersion(frameworkModel.getLatestVersion());
        javascriptFramework.setRating(frameworkModel.getRating());
        return javascriptFramework;
    }

    private List<FrameworkVersion> convertModelToEntityList(List<FrameworkVersionModel> versionsList,
                                                            JavascriptFramework framework) {

        List<FrameworkVersion> versions = new ArrayList<>();
        for (FrameworkVersionModel model : versionsList) {
            FrameworkVersion version = new FrameworkVersion(model.getVersion(), model.getReleaseDate());
            version.setFramework(framework);
            versionRepository.save(version);

            versions.add(version);
        }
        return versions;
    }


    private List<FrameworkVersionModel> convertEntityToModelList(List<FrameworkVersion> versions) {
        List<FrameworkVersionModel> list = new ArrayList<>();
        for (FrameworkVersion version : versions) {
            list.add(new FrameworkVersionModel(version.getId(),
                version.getVersion(),
                version.getReleaseDate()));
        }
        return list;
    }

}
