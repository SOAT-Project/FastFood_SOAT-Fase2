package soat.project.fastfoodsoat.application.usecase.product.create;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

@Component
public class DefaultCreateProductUseCase extends CreateProductUseCase {

    private final ProductGateway productGateway;

    public DefaultCreateProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }


    @Override
    public CreateProductOutput execute(final CreateProductCommand command) {
        System.out.println("CreateProductCommand: " + command);
        final var name = command.name();
        final var description = command.description();
        final var value = command.value();
        final var imageURL = command.imageURL();
        final var productCategoryId = ProductCategoryId.of(command.productCategoryId());

        final var notification = Notification.create();

        final var product = notification.validate(() ->
                Product.newProduct(name, description, value, imageURL, productCategoryId)
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create product", notification);
        }

        return CreateProductOutput.from(this.productGateway.create(product));
    }
}