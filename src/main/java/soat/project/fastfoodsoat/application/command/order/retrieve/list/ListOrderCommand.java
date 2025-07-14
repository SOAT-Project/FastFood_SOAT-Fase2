package soat.project.fastfoodsoat.application.command.order.retrieve.list;

import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public record ListOrderCommand(SearchQuery searchQuery) {
}
