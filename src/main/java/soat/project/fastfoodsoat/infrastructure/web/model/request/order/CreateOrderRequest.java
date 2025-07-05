package soat.project.fastfoodsoat.infrastructure.web.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        @JsonProperty("client_public_id") UUID clientPublicId,

        @NotEmpty(message = "field 'products' cannot be empty")
        @Valid
        @JsonProperty("products") List<CreateOrderProductRequest> orderProducts
){}
