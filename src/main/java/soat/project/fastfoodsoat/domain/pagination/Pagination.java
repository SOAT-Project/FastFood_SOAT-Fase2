package soat.project.fastfoodsoat.domain.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        @JsonProperty("current_page") int currentPage,
        @JsonProperty("per_page") int perPage,
        @JsonProperty("total") long total,
        @JsonProperty("items") List<T> items
) {
    public <R> Pagination<R> map(final Function<T, R> mapper) {
        final var aNewList = this.items.stream()
                .map(mapper)
                .toList();
        return new Pagination<>(currentPage, perPage, total, aNewList);
    }

    public static <T> Pagination<T> with(
            int currentPage,
            int perPage,
            long total,
            List<T> items
    ) {
        return new Pagination<>(currentPage, perPage, total, items);
    }

}