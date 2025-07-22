package soat.project.fastfoodsoat.infrastructure.web.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import soat.project.fastfoodsoat.infrastructure.web.model.DefaultApiError;
import soat.project.fastfoodsoat.infrastructure.web.model.request.payment.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.MercadoPagoWebhookResponse;

@Tag(name = "Webhooks Mocks")
@RequestMapping("/mock")
public interface PaymentWebhookMockAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Simulate Mercado Pago Webhook",
            description = "Simulates a Mercado Pago webhook notification for testing purposes."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Webhook notification processed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource not found",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
            }
    )
    ResponseEntity<MercadoPagoWebhookResponse> simulateMercadoPagoWebhook(@RequestBody @Valid MercadoPagoWebhookRequest request);
}
