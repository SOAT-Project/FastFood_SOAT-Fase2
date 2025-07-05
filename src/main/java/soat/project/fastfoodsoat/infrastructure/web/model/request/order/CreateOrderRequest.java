package soat.project.fastfoodsoat.infrastructure.web.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @JsonProperty("client_public_id") UUID clientPublicId,
        @JsonProperty("products") List<CreateOrderProductRequest> orderProducts
){}
