package io.github.dougllasfps.imageliteapi.infra.reposutory;

import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.github.dougllasfps.imageliteapi.infra.reposutory.specs.GenericSpecs.conjunction;
import static io.github.dougllasfps.imageliteapi.infra.reposutory.specs.ImageSpecs.nameLike;
import static io.github.dougllasfps.imageliteapi.infra.reposutory.specs.ImageSpecs.tagsLike;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        Specification<Image> spec = where( conjunction() );

        if (extension != null ) {
            Specification<Image> extensionEqual = ((root, query1, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("extension"), extension));
            spec = spec.and(extensionEqual);
        }

        if (StringUtils.hasText(query)) {
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }

}
