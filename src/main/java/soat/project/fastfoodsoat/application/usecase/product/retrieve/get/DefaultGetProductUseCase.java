package soat.project.fastfoodsoat.application.usecase.product.retrieve.get;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.UseCase;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.function.Supplier;

@Component
public class DefaultGetProductUseCase extends UseCase<Integer, GetProductOutput> {

    private final ProductGateway productGateway;

    public DefaultGetProductUseCase(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }


    @Override
    public GetProductOutput execute(Integer command) {
        final var productId = ProductId.of(command);

        return this.productGateway
                .findById(productId)
                .map(GetProductOutput::from)
                .orElseThrow(notFound(productId));
    }

    private Supplier<NotFoundException> notFound(final ProductId id) {
        return () -> NotFoundException.with(Product.class, id);
    }
}
