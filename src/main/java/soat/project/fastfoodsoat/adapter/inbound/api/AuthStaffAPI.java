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
import soat.project.fastfoodsoat.adapter.inbound.api.model.AuthStaffRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.AuthStaffResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.DefaultApiError;

@Tag(name = "Auth")
@RequestMapping("/auths")
public interface AuthStaffAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Auth Staff",
            description = "Authenticate Staff and retrieve authentication token"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token generated successfully",
                            content = @Content(schema = @Schema(implementation = AuthStaffResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Staff was not found",
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
    ResponseEntity<AuthStaffResponse> authenticate(@RequestBody AuthStaffRequest authRequest);

}
