package pe.edu.cibertec.patita_frontend_wc_a.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientAutenticacion(WebClient.Builder builder) {
      return   builder.build();
    }
}
