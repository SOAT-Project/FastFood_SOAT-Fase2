package soat.project.fastfoodsoat.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MercadoPagoConfig {

    @Value("${mercadopago.token}")
    private String token;

    @Value("${mercadopago.collectorId}")
    private String collectorId;

    @Value("${mercadopago.posId}")
    private String posId;

    @Value("${mercadopago.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient mercadoPagoWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public String getCollectorId() {
        return collectorId;
    }

    public String getPosId() {
        return posId;
    }
}
