package soat.project.fastfoodsoat.infrastructure.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soat.project.fastfoodsoat.infrastructure.web.model.DefaultApiError;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.GetPaymentStatusByExternalReferenceResponse;

@Tag(name="Payments")
@RequestMapping(value="payments")
public interface PaymentAPI {

    @GetMapping(
            value = "qrcode",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get payment QRCode by external reference")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment QRCode retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    ResponseEntity<byte[]> getQrCodeByExternalReference(@RequestParam("external_reference") String externalReference);

    @GetMapping(
            value = "status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get payment status by external reference")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment status retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment not found",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = DefaultApiError.class))
            )
    })
    ResponseEntity<GetPaymentStatusByExternalReferenceResponse> getStatusByExternalReference(@RequestParam("external_reference") String external_reference);
}
