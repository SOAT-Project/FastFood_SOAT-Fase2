package soat.project.fastfoodsoat.adapter.inbound.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateOrderRequest(
        @JsonProperty("client_id") Integer clientId,
        @JsonProperty("products") List<CreateOrderProductRequest> orderProducts
){}
