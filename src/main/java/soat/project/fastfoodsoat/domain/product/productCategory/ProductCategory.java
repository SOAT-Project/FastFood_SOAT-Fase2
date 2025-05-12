package soat.project.fastfoodsoat.domain.product.productCategory;

import soat.project.fastfoodsoat.domain.Entity;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.time.Instant;

public class ProductCategory extends Entity<ProductCategoryId> {

    private String name;

    protected ProductCategory(final ProductCategoryId productCategoryId,
                              final Instant createdAt,
                              final Instant updatedAt,
                              final Instant deletedAt,
                              final String name) {

        super(
                productCategoryId,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.name = name;
        this.selfValidation();


    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProductCategoryValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create product categorie", notification);
    }

    public String getName() {
        return name;
    }
}
