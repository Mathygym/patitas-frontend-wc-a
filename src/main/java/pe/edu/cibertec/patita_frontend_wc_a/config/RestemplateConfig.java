package pe.edu.cibertec.patita_frontend_wc_a.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestemplateConfig {

    @Bean
    public RestTemplate restTemplateAutenticacion(RestTemplateBuilder builder){

        return  builder
                .rootUri("http://localhost:8081/autenticacion")
                .setConnectTimeout(Duration.ofSeconds(5))//tiempo de esepra maximo para establecer la coneccion
                .setReadTimeout(Duration.ofSeconds(10)) // es el tiempo de espera maximo para resivir la respuesta total
                .build();
        //return new RestTemplate();
    }


    @Bean
    public RestTemplate restTemplateFinanzas(RestTemplateBuilder builder){

        return  builder
                .rootUri("http://localhost:8085/finanzas")
                .setReadTimeout(Duration.ofSeconds(30))
                .build();

    }

    @Bean
    public RestTemplate restTemplateReporteria(RestTemplateBuilder builder){

        return  builder
                .rootUri("http://localhost:8086/reporteria")
                .setReadTimeout(Duration.ofSeconds(60))
                .build();

    }
}
