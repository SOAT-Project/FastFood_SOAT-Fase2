package soat.project.fastfoodsoat.application.command.product;

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
