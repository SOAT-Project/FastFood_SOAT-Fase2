package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

@Component
public class DefaultListOrderUseCase extends ListOrderUseCase {

    private final OrderGateway orderGateway;

    public DefaultListOrderUseCase(final OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public Pagination<ListOrderOutput> execute(final ListOrderParams params) {
        final var onlyPaid = params.onlyPaid();
        final var query = params.searchQuery();

        return orderGateway.findAll(onlyPaid, query)
                .map(ListOrderOutput::from);
    }

}
