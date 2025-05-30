package soat.project.fastfoodsoat.adapter.inbound.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soat.project.fastfoodsoat.adapter.inbound.api.model.DefaultApiError;
import soat.project.fastfoodsoat.adapter.inbound.api.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.request.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.UpdateOrderStatusResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

@Tag(name = "Orders")
@RequestMapping("/orders")
public interface OrderAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a new order"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order generated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "A validation error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "An entity was not found",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    )
        }
    )
    ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest orderRequest);

  
    @PutMapping(
            value = "/{publicId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update order status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status updated successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
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
            )
    })
    ResponseEntity<UpdateOrderStatusResponse> updateStatus(
            @PathVariable String publicId,
            @RequestBody UpdateOrderStatusRequest request
    );
  

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "List orders"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Orders listed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultApiError.class))
                    )
            }
    )
    ResponseEntity<Pagination<ListOrderResponse>> list(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "per_page", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "orderNumber") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

}
