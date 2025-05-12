package soat.project.fastfoodsoat.application.usecase.product.update;

import java.math.BigDecimal;

public record UpdateProductCommand(
        String name,
        String description,
        BigDecimal value,
        String imageURL,
        Integer productCategoryId
) {

    public static UpdateProductCommand with(
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final Integer productCategoryId
    ) {
        return new UpdateProductCommand(
                name,
                description,
                value,
                imageURL,
                productCategoryId
        );
    }
}