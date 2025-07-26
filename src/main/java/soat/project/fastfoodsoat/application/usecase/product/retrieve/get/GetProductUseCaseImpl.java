package soat.project.fastfoodsoat.application.usecase.product.retrieve.get;

import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.application.output.product.GetProductOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.function.Supplier;

public class GetProductUseCaseImpl extends GetProductUseCase {

    private final ProductRepositoryGateway productRepositoryGateway;

    public GetProductUseCaseImpl(final ProductRepositoryGateway productRepositoryGateway) {
        this.productRepositoryGateway = productRepositoryGateway;
    }

    @Override
    public GetProductOutput execute(Integer command) {
        final var productId = ProductId.of(command);

        return this.productRepositoryGateway
                .findById(productId)
                .map(GetProductOutput::from)
                .orElseThrow(notFound(productId));
    }

    private Supplier<NotFoundException> notFound(final ProductId id) {
        return () -> NotFoundException.with(Product.class, id);
    }

}
