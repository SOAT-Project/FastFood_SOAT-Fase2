package soat.project.fastfoodsoat.infrastructure.persistence;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.mapper.ProductMapper;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryGatewayImpl implements ProductRepositoryGateway {

    private final ProductRepository productRepository;

    public ProductRepositoryGatewayImpl(final ProductRepository productRepository) {
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
                .map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findByIds(List<Integer> productIds) {
        return this.productRepository.findAll(Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            return root.get("id").in(productIds);
        })).stream()
                .map(ProductMapper::toDomain)
                .toList();
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

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ProductMapper::toDomain).toList()
        );

    }

    @Override
    public Pagination<Product> findAll(final SearchQuery searchQuery) {
        return null;
    }

    private Product save(final Product product){
        final var saveProduct = productRepository.save(ProductMapper.fromDomain(product));
        return ProductMapper.toDomain(saveProduct);
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
