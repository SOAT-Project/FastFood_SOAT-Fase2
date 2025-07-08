package soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff;

import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderForStaffCommand;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderForStaffOutput;
import soat.project.fastfoodsoat.application.usecase.UseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

public abstract class ListOrderForStaffUseCase extends UseCase<ListOrderForStaffCommand, Pagination<ListOrderForStaffOutput>> {
}
