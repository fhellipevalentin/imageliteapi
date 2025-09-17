package io.github.dougllasfps.imageliteapi.infra.reposutory;

import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        //SELECT * FROM IMAGE WHERE 1 = 1
        Specification<Image> conjunction = ((root, query1, criteriaBuilder) -> criteriaBuilder.conjunction());
        Specification<Image> spec = Specification.where( conjunction );

        if (extension != null ) {
            Specification<Image> extensionEqual = ((root, query1, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("extension"), extension));
            spec = spec.and(extensionEqual);
        }

        if (StringUtils.hasText(query)) {
            // AND ( NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY' )
            // RIVER => %RI%
            Specification<Image> nameLike = (root, query1, criteriaBuilder) -> criteriaBuilder.like( criteriaBuilder.upper(root.get("name")), "%" + query.toLowerCase() + "%");
            Specification<Image> tagsLike = (root, query1, criteriaBuilder) -> criteriaBuilder.like( criteriaBuilder.upper(root.get("tags")), "%" + query.toLowerCase() + "%");

            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);

            spec = spec.and(nameOrTagsLike);
        }

        return findAll(spec);
    }

}
