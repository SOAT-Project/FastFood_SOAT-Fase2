package soat.project.fastfoodsoat.infrastructure.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soat.project.fastfoodsoat.application.gateway.ProductCategoryRepositoryGateway;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.product.delete.DeleteProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.delete.DeleteProductUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory.ListByCategoryUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory.ListByCategoryUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductUseCaseImpl;

@Configuration
public class ProductUseCaseConfig {

    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductCategoryRepositoryGateway productCategoryRepositoryGateway;

    public ProductUseCaseConfig(
            final ProductRepositoryGateway productRepositoryGateway,
            final ProductCategoryRepositoryGateway productCategoryRepositoryGateway
    ) {
        this.productRepositoryGateway = productRepositoryGateway;
        this.productCategoryRepositoryGateway = productCategoryRepositoryGateway;
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new CreateProductUseCaseImpl(productRepositoryGateway, productCategoryRepositoryGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DeleteProductUseCaseImpl(productRepositoryGateway);
    }

    @Bean
    public GetProductUseCase getProductUseCase() {
        return new GetProductUseCaseImpl(productRepositoryGateway);
    }

    @Bean
    public ListByCategoryUseCase listByCategoryUseCase() {
        return new ListByCategoryUseCaseImpl(productRepositoryGateway, productCategoryRepositoryGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new UpdateProductUseCaseImpl(productRepositoryGateway);
    }
}
