package soat.project.fastfoodsoat.application.usecase.product.delete;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;

@Component
public class DefaultDeleteProductUseCase extends DeleteProductUseCase{

    private final ProductGateway productGateway;

    public DefaultDeleteProductUseCase(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(Integer command) {
        final var productId = ProductId.of(command);
        productGateway.deleteById(productId);
    }
}
