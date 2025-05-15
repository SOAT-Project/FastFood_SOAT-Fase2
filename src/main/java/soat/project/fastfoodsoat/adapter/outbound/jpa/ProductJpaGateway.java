package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.Optional;

@Component
public class ProductJpaGateway implements ProductGateway {

    private final ProductRepository productRepository;

    public ProductJpaGateway(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(final Product product) {
        return save(product);
    }

    @Override
    public Product update(final Product product) {
        return save(product);
    }

    @Override
    public void deleteById(final ProductId productId) {
        this.productRepository.deleteById(productId.getValue());
    }

    @Override
    public Optional<Product> findById(final ProductId productId) {
        return this.productRepository
                .findById(productId.getValue())
                .map(ProductJpaEntity::toDomain);
    }

    @Override
    public Pagination<Product> findProductByCategory(final ProductCategoryId productCategoryId, final SearchQuery query) {
        final var pageRequest = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(Specification.where(null))
                .and((root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.equal(
                            root.get("productCategoryId"),
                            productCategoryId.getValue()
                    );
                });

        final var pageResult = this.productRepository.findAll(Specification.where(specification), pageRequest);

        System.out.println("Page: " + query.page());
        System.out.println("PerPage: " + query.perPage());
        System.out.println("Sort: " + query.sort());
        System.out.println("Direction: " + query.direction());

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ProductJpaEntity::toDomain).toList()
        );

    }

    @Override
    public Pagination<Product> findAll(final SearchQuery searchQuery) {
        return null;
    }

    private Product save(final Product product){
        return this.productRepository.save(ProductJpaEntity.fromDomain(product)).toDomain();
    }

    private Specification<ProductJpaEntity> assembleSpecification(final String str) {
        final Specification<ProductJpaEntity> nameLike = dynamicLike("name", str);
        final Specification<ProductJpaEntity> descriptionLike = dynamicLike("description", str);
        return nameLike.or(descriptionLike);
    }

    private Specification<ProductJpaEntity> dynamicLike(final String field, final String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }


}
