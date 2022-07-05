package com.example.pokemonapi.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "filter")
@Getter
@Setter
@Validated
public class FilterResultProperties {

    @NotBlank
    private String languageDescriptions;
}
