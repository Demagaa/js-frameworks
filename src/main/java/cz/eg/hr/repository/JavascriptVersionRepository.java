package cz.eg.hr.repository;

import cz.eg.hr.data.FrameworkVersion;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavascriptVersionRepository extends CrudRepository<FrameworkVersion, Long>, JpaSpecificationExecutor<FrameworkVersion> {

}
