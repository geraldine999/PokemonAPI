package com.example.pokemonapi.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    private String name;
    private List<PokemonType> types;
    private int weight;
    private List<PokemonAbility> abilities;
    private List<PokemonMove> moves;
    private List<PokemonStat> stats;



}
