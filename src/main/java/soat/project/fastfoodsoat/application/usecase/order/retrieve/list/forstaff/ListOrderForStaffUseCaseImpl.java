package soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderCommand;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderForStaffCommand;
import soat.project.fastfoodsoat.application.gateway.OrderRepositoryGateway;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderForStaffOutput;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

@Component
public class ListOrderForStaffUseCaseImpl extends ListOrderForStaffUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;

    public ListOrderForStaffUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway) {
        this.orderRepositoryGateway = orderRepositoryGateway;
    }

    @Override
    public Pagination<ListOrderForStaffOutput> execute(ListOrderForStaffCommand command) {
        final var onlyPaid = command.onlyPaid();
        final var query = command.searchQuery();

        return orderRepositoryGateway.findAllForStaff(onlyPaid, query)
                .map(ListOrderForStaffOutput::from);
    }
}
