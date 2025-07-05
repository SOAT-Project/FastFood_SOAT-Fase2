package soat.project.fastfoodsoat.application.usecase.product.update;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.product.update.UpdateProductCommand;
import soat.project.fastfoodsoat.application.output.product.update.UpdateProductOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.util.function.Supplier;

@Component
public class UpdateProductUseCaseImpl extends UpdateProductUseCase{

    private final ProductRepositoryGateway productRepositoryGateway;

    public UpdateProductUseCaseImpl(final ProductRepositoryGateway productRepositoryGateway) {
        this.productRepositoryGateway = productRepositoryGateway;
    }

    @Override
    public UpdateProductOutput execute(UpdateProductCommand command) {
        final var productId = ProductId.of(command.id());
        final var name = command.name();
        final var description = command.description();
        final var value = command.value();
        final var imageURL = command.imageURL();
        final var productCategoryId = ProductCategoryId.of(command.productCategoryId());

        final var product = this.productRepositoryGateway.findById(productId).orElseThrow(notFound(productId));
        final var notification = Notification.create();
        product.update(name, description, value, imageURL, productCategoryId);
        product.validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("could not update product", notification);
        }

        return UpdateProductOutput.from(this.productRepositoryGateway.update(product));
    }

    private Supplier<NotFoundException> notFound(final ProductId id) {
        return () -> NotFoundException.with(Product.class, id);
    }
}
