package soat.project.fastfoodsoat.adapter.inbound.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="Payments")
@RequestMapping(value="payments")
public interface PaymentAPI {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get payment QRCode by order public ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment QRCode retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<byte[]> getByOrderPublicId(@RequestParam("order_public_id") String orderPublicId);
}
