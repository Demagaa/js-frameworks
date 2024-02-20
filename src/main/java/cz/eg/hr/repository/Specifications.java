package cz.eg.hr.repository;

import cz.eg.hr.data.FrameworkVersion;
import cz.eg.hr.data.JavascriptFramework;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {
    public static Specification<JavascriptFramework> hasId (Long id) {
        return id == null ? ((root, query, criteriaBuilder) -> null) :
            ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<JavascriptFramework> hasName (String name) {
        return name == null ? ((root, query, criteriaBuilder) -> null) :
            ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.<String>get("name"), name));
    }

    public static Specification<JavascriptFramework> hasLatestVersion (String latestVersion) {
        return latestVersion == null ? ((root, query, criteriaBuilder) -> null) :
            ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.<String>get("latestVersion"), latestVersion));
    }

    public static Specification<JavascriptFramework> hasRating (Integer rating) {
        return rating == null ? ((root, query, criteriaBuilder) -> null) :
            ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating));
    }
    public static Specification<JavascriptFramework> hasVersion(String version) {
        return (root, query, criteriaBuilder) -> {
            if (version == null) {
                return null;
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<JavascriptFramework> subRoot = subquery.from(JavascriptFramework.class);
            Join<JavascriptFramework, FrameworkVersion> versionsJoin = subRoot.join("versions", JoinType.INNER);

            subquery.select(subRoot.get("id"))
                .where(criteriaBuilder.equal(versionsJoin.get("version"), version));

            return criteriaBuilder.in(root.get("id")).value(subquery);
        };
    }
}
