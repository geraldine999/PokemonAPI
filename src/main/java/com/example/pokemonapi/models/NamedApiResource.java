package com.example.pokemonapi.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NamedApiResource {

    private String name;
    private String url;
}
