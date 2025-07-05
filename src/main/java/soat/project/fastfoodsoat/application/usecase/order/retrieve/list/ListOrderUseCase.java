package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import soat.project.fastfoodsoat.application.usecase.UseCase;
import soat.project.fastfoodsoat.application.command.order.ListOrderParams;
import soat.project.fastfoodsoat.application.output.order.ListOrderOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public abstract class ListOrderUseCase extends UseCase<ListOrderParams, Pagination<ListOrderOutput>> {
}
