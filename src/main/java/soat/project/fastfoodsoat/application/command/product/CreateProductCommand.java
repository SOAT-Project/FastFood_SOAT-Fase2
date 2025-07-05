package soat.project.fastfoodsoat.application.command.product;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal value,
        String imageURL,
        Integer productCategoryId
) {

    public static CreateProductCommand with(
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final Integer productCategoryId
    ) {
        return new CreateProductCommand(
                name,
                description,
                value,
                imageURL,
                productCategoryId
        );
    }
}