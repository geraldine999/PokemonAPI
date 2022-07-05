package com.example.pokemonapi.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Stat {

    private List<NamedApiResource> characteristics;
}
