package soat.project.fastfoodsoat.adapter.inbound.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @JsonProperty("client_public_id") UUID clientPublicId,
        @JsonProperty("products") List<CreateOrderProductRequest> orderProducts
){}
