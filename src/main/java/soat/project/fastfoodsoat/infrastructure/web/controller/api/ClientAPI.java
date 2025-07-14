package soat.project.fastfoodsoat.infrastructure.web.controller.api;

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
import soat.project.fastfoodsoat.infrastructure.web.model.*;
import soat.project.fastfoodsoat.infrastructure.web.model.request.client.ClientAuthRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.request.client.CreateClientRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.client.ClientAuthResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.client.CreateClientResponse;

@Tag(name = "Clients")
@RequestMapping("/clients")
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
                            description = "Client found with success"
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
    ResponseEntity<ClientAuthResponse> findClientByCPF(@Valid @RequestBody ClientAuthRequest clientAuthRequest);

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Client created successfully"
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
            )
    })
    ResponseEntity<CreateClientResponse> create(@Valid @RequestBody CreateClientRequest request);
}
