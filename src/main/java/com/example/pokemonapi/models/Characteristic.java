package com.example.pokemonapi.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Characteristic {

    private List<Description> descriptions;
}
