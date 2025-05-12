package soat.project.fastfoodsoat.domain.product;

import soat.project.fastfoodsoat.domain.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class Product extends AggregateRoot<ProductId> {

    private String name;
    private String description;
    private BigDecimal value;
    private String imageURL;
    private ProductCategoryId productCategoryId;

    protected Product(
            final ProductId productId,
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final ProductCategoryId productCategoryId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {

        super(
                productId,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.name = name;
        this.description = description;
        this.value = value;
        this.imageURL = imageURL;
        this.productCategoryId = productCategoryId;
        this.selfValidation();
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create a product", notification);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ProductCategoryId getProductCategorieId() {
        return productCategoryId;
    }
}
