package soat.project.fastfoodsoat.application.usecase.product.delete;

import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;

public class DeleteProductUseCaseImpl extends DeleteProductUseCase{

    private final ProductRepositoryGateway productRepositoryGateway;

    public DeleteProductUseCaseImpl(final ProductRepositoryGateway productRepositoryGateway) {
        this.productRepositoryGateway = productRepositoryGateway;
    }

    @Override
    public void execute(Integer command) {
        final var productId = ProductId.of(command);
        productRepositoryGateway.deleteById(productId);
    }
}
