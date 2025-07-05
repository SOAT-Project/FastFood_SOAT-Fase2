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
import soat.project.fastfoodsoat.infrastructure.web.model.request.payment.UpdatePaymentToPaidStatusRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.UpdatePaymentToPaidStatusResponse;

@Tag(name="Payments")
@RequestMapping(value="payments")
public interface PaymentAPI {

    @GetMapping(
            value = "/{external_reference}",
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
    ResponseEntity<byte[]> getByExternalReference(@PathVariable("external_reference") String externalReference);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update payment to paid status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment status updated successfully"
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
    ResponseEntity<UpdatePaymentToPaidStatusResponse> updateToPaidStatus(@RequestBody UpdatePaymentToPaidStatusRequest request);

}
