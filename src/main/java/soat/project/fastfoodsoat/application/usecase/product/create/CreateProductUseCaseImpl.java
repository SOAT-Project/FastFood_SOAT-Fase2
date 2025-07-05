package soat.project.fastfoodsoat.application.usecase.product.create;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.product.CreateProductCommand;
import soat.project.fastfoodsoat.application.output.product.CreateProductOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategory;
import soat.project.fastfoodsoat.application.gateway.ProductCategoryRepositoryGateway;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

@Component
public class CreateProductUseCaseImpl extends CreateProductUseCase {

    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductCategoryRepositoryGateway categoryGateway;

    public CreateProductUseCaseImpl(final ProductRepositoryGateway productRepositoryGateway,
                                    final ProductCategoryRepositoryGateway categoryGateway) {
        this.productRepositoryGateway = productRepositoryGateway;
        this.categoryGateway = categoryGateway;
    }


    @Override
    public CreateProductOutput execute(final CreateProductCommand command) {
        final var name = command.name();
        final var description = command.description();
        final var value = command.value();
        final var imageURL = command.imageURL();
        final var productCategoryId = ProductCategoryId.of(command.productCategoryId());

        final var category = categoryGateway.findById(productCategoryId)
                .orElseThrow(() -> NotFoundException.with(ProductCategory.class, productCategoryId));

        final var notification = Notification.create();

        final var product = notification.validate(() ->
                Product.newProduct(name, description, value, imageURL, category.getId())
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create product", notification);
        }

        return CreateProductOutput.from(this.productRepositoryGateway.create(product));
    }
}