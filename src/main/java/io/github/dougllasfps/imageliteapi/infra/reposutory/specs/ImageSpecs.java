package io.github.dougllasfps.imageliteapi.infra.reposutory.specs;

import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

public class ImageSpecs {

    private ImageSpecs() {

    }

    public static Specification<Image> extensionEqual(ImageExtension extension) {
        return (root, query1, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("extension"), extension);
    }

    public static Specification<Image> nameLike(String name) {
        return (root, query1, criteriaBuilder) -> criteriaBuilder.like( criteriaBuilder.upper(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Image> tagsLike(String tags) {
        return (root, query1, criteriaBuilder) -> criteriaBuilder.like( criteriaBuilder.upper(root.get("tags")), "%" + tags.toLowerCase() + "%");
    }



}
