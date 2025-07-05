package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.order.ListOrderParams;
import soat.project.fastfoodsoat.application.gateway.OrderRepositoryGateway;
import soat.project.fastfoodsoat.application.output.order.ListOrderOutput;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

@Component
public class ListOrderUseCaseImpl extends ListOrderUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;

    public ListOrderUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway) {
        this.orderRepositoryGateway = orderRepositoryGateway;
    }

    @Override
    public Pagination<ListOrderOutput> execute(final ListOrderParams params) {
        final var onlyPaid = params.onlyPaid();
        final var query = params.searchQuery();

        return orderRepositoryGateway.findAll(onlyPaid, query)
                .map(ListOrderOutput::from);
    }

}
