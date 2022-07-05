package com.example.pokemonapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@OpenAPIDefinition(
        info = @Info(title = "Pokemon API", version = "1.0",
                contact = @Contact(name = "Geraldine Adelmann",
                email = "gadelmann@agro.uba.ar",
                url = "https://www.linkedin.com/in/geraldineadelmann/"))
)
@SpringBootApplication
public class PokemonApiApplication {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer-> configurer.defaultCodecs()
                                //raises the buffer size limit to 16MB (default is 256KB)
                                .maxInMemorySize(16*1024*1024))
                        .build())
                .build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(PokemonApiApplication.class, args);
    }

}
