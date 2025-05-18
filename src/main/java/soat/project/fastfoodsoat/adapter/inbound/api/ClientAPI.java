package soat.project.fastfoodsoat.adapter.inbound.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import soat.project.fastfoodsoat.adapter.inbound.api.model.*;

@Tag(name = "Client")
@RequestMapping("/client")
public interface ClientAPI {

    @PostMapping(
            value = "/identify",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find a client by CPF",
            description = "Find a client by CPF"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client found with success",
                            content = @Content(schema = @Schema(implementation = ClientAuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client was not found",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "A validation error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
            }
    )
    ResponseEntity<ClientAuthResponse> findClientByCPF(@RequestBody ClientAuthRequest clientAuthRequest);
}
