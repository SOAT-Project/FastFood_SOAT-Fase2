package soat.project.fastfoodsoat.application.command.product.retrieve.list.bycategory;

import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public record ListByCategoryCommand(
        Integer productCategoryId,
        SearchQuery searchQuery
) {

    public static ListByCategoryCommand with(final Integer productCategoryId,
                                             final SearchQuery searchQuery)
    {
        return new ListByCategoryCommand(productCategoryId, searchQuery);
    }
}
