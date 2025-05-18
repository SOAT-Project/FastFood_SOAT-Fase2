package soat.project.fastfoodsoat.adapter.inbound.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soat.project.fastfoodsoat.adapter.inbound.api.model.*;
import soat.project.fastfoodsoat.domain.pagination.Pagination;

@Tag(name="Products")
@RequestMapping(value="products")
public interface ProductAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest request);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<GetProductResponse> getById(@PathVariable Integer id);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "422", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<UpdateProductResponse> update(@PathVariable Integer id, @RequestBody UpdateProductRequest request);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    void delete(@PathVariable Integer id);

    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List products by category with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Pagination<ListProductByCategoryResponse> listByCategory(
            @PathVariable(name = "categoryId") Integer categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "perPage", defaultValue = "10") int perPage,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "dir", defaultValue = "asc") String dir,
            @RequestParam(name = "search", defaultValue = "") String search
    );
}



