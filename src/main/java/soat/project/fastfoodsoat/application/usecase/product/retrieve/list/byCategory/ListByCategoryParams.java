package soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory;

import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public record ListByCategoryParams(
        Integer productCategoryId,
        SearchQuery searchQuery
) {

    public static ListByCategoryParams with(final Integer productCategoryId,
                                            final SearchQuery searchQuery)
    {
        return new ListByCategoryParams(productCategoryId, searchQuery);
    }
}
