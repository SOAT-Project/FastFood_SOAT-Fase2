package soat.project.fastfoodsoat.application.output.product.update;

import soat.project.fastfoodsoat.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateProductOutput(
        Integer id,
        String name,
        String description,
        BigDecimal value,
        String imageURL,
        Integer productCategoryId,
        Instant updatedAt
) {

    public static UpdateProductOutput from(
            final Integer id,
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final Integer productCategorieId,
            final Instant updatedAt
    ) {
        return new UpdateProductOutput(id, name, description, value, imageURL, productCategorieId, updatedAt);
    }

    public static UpdateProductOutput from(final Product product) {
        return new UpdateProductOutput(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getValue(),
                product.getImageURL(),
                product.getProductCategoryId().getValue(),
                product.getUpdatedAt()
        );
    }
}