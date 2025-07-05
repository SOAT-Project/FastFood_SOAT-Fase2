package soat.project.fastfoodsoat.domain.product;

import soat.project.fastfoodsoat.domain.shared.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;
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

    public static Product newProduct(
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final ProductCategoryId productCategoryId
    ) {
        final ProductId productId = null;
        final var now = Instant.now();
        return new Product(
                productId,
                name,
                description,
                value,
                imageURL,
                productCategoryId,
                now,
                now,
                null
        );
    }

    public static Product with(
            final ProductId productId,
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final ProductCategoryId productCategoryId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Product(
                productId,
                name,
                description,
                value,
                imageURL,
                productCategoryId,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Product update(
            final String name,
            final String description,
            final BigDecimal value,
            final String imageURL,
            final ProductCategoryId productCategoryId
    ) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.imageURL = imageURL;
        this.productCategoryId = productCategoryId;
        this.updatedAt = Instant.now();

        return this;
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

    public ProductCategoryId getProductCategoryId() {
        return productCategoryId;
    }
}
