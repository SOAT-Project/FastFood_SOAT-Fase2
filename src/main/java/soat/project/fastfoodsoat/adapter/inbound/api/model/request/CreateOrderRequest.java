package soat.project.fastfoodsoat.adapter.inbound.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public record CreateOrderRequest(
        @JsonProperty("client_id") Optional<Integer> clientId,
        @JsonProperty("products") List<CreateOrderProductRequest> orderProducts
){}
