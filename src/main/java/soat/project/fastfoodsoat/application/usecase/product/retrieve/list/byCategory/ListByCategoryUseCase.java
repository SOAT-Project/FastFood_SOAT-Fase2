package soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory;

import soat.project.fastfoodsoat.application.UseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public abstract class ListByCategoryUseCase
        extends UseCase<ListByCategoryParams, Pagination<ListByCategoryOutput>> {
}
