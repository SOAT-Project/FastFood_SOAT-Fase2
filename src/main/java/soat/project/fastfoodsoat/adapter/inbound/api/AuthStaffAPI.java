package soat.project.fastfoodsoat.adapter.inbound.api;

import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Auth")
@RequestMapping("/auth")
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
                    @ApiResponse(responseCode = "200", description = "Token generated successfully"),
                    @ApiResponse(responseCode = "404", description = "Staff was not found"),
                    @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
            }
    )
    ResponseEntity<AuthStaffResponse> authenticate(@RequestBody AuthStaffRequest authRequest);

}
