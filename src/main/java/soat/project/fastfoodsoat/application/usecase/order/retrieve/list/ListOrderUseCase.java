package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import soat.project.fastfoodsoat.application.usecase.UseCase;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderCommand;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public abstract class ListOrderUseCase extends UseCase<ListOrderCommand, Pagination<ListOrderOutput>> {
}
