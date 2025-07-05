package soat.project.fastfoodsoat.application.command.order;

import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public record ListOrderParams(boolean onlyPaid, SearchQuery searchQuery) {
}
