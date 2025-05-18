package soat.project.fastfoodsoat.application.usecase.product.retrieve.get;

import soat.project.fastfoodsoat.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record GetProductOutput(
        Integer id,
        String name,
        String description,
        BigDecimal value,
        String imageURL,
        Integer productCategoryId,
        Instant createdAt,
        Instant updatedAt
) {

    public static GetProductOutput from(
            final Integer id,
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final Integer productCategorieId,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new GetProductOutput(id, name, description, value, imageURL, productCategorieId, createdAt, updatedAt);
    }

    public static GetProductOutput from(final Product product) {
        return new GetProductOutput(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getValue(),
                product.getImageURL(),
                product.getProductCategoryId().getValue(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
