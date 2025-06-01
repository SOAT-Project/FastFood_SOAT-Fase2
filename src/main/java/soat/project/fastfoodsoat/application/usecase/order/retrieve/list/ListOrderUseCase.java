package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import soat.project.fastfoodsoat.application.UseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public abstract class ListOrderUseCase extends UseCase<ListOrderParams, Pagination<ListOrderOutput>> {
}
