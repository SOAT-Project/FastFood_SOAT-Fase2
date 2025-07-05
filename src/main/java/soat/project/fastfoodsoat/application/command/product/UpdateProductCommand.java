package soat.project.fastfoodsoat.application.command.product;

import java.math.BigDecimal;

public record UpdateProductCommand(
        Integer id,
        String name,
        String description,
        BigDecimal value,
        String imageURL,
        Integer productCategoryId
) {

    public static UpdateProductCommand with(
            Integer id,
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final Integer productCategoryId
    ) {
        return new UpdateProductCommand(
                id,
                name,
                description,
                value,
                imageURL,
                productCategoryId
        );
    }
}