package soat.project.fastfoodsoat.application.usecase.order.retrieve.list;

import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public record ListOrderParams(boolean onlyPaid, SearchQuery searchQuery) {
}
