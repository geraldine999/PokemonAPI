package com.example.pokemonapi.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Description {

    private String description;
    private Language language;
}
