package soat.project.fastfoodsoat.adapter.inbound.api.model;

import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.time.Instant;
import java.util.List;

public record DefaultApiError(
        Instant timestamp,
        Integer status,
        List<DomainError> errors
) {
}
