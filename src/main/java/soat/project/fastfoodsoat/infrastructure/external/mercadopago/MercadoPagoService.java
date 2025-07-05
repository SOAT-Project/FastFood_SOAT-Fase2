package soat.project.fastfoodsoat.infrastructure.external.mercadopago;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import soat.project.fastfoodsoat.infrastructure.config.MercadoPagoConfig;
import soat.project.fastfoodsoat.infrastructure.external.mercadopago.model.CreateDynamicQrCodeResponse;
import soat.project.fastfoodsoat.domain.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.application.gateway.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class MercadoPagoService implements PaymentService {

    private final WebClient webClient;
    private final String collectorId;
    private final String posId;
    private final ObjectMapper objectMapper;

    public MercadoPagoService(WebClient mercadoPagoWebClient, MercadoPagoConfig mercadoPagoConfig) {
        this.webClient = mercadoPagoWebClient;
        this.collectorId = mercadoPagoConfig.getCollectorId();
        this.posId = mercadoPagoConfig.getPosId();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String createDynamicQrCode(Integer orderNumber, UUID publicId, BigDecimal value, List<OrderProduct> orderProducts) {
        try {
            final String path = String.format("/instore/orders/qr/seller/collectors/%s/pos/%s/qrs", collectorId, posId);

            Map<String, Object> requestBody = Map.of(
                    "title", "Order " + orderNumber,
                    "description", "Order " + orderNumber,
                    "external_reference", publicId.toString(),
                    "total_amount", value,
                    "items", orderProducts.stream().map(
                            orderProduct -> Map.of(
                                    "title", orderProduct.getProduct().getName(),
                                    "total_amount", orderProduct.getValue(),
                                    "quantity", orderProduct.getQuantity(),
                                    "unit_price", orderProduct.getValue().divide(BigDecimal.valueOf(orderProduct.getQuantity())),
                                    "unit_measure", "unit"
                            )
                    ).toList()
            );

            String bodyJson = objectMapper.writeValueAsString(requestBody);

            final CreateDynamicQrCodeResponse response = webClient.post()
                    .uri(path)
                    .contentType(APPLICATION_JSON)
                    .bodyValue(bodyJson)
                    .retrieve()
                    .bodyToMono(CreateDynamicQrCodeResponse.class)
                    .block();

            return response.getQrCode();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON for Mercado Pago payment", e);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Mercado Pago API error: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during Mercado Pago payment", e);
        }
    }
}
