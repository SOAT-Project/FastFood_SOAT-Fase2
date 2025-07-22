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

@Tag(name = "Webhooks")
@RequestMapping(value = "/webhooks/mercadopago")
public interface PaymentWebhookAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Receives Mercado Pago payment notifications",
            description = "Endpoint for Mercado Pago to send payment status updates."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification processed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Payment not found",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
            }
    )
    ResponseEntity<MercadoPagoWebhookResponse> handlePaymentNotification(@RequestBody @Valid MercadoPagoWebhookRequest request);
}
