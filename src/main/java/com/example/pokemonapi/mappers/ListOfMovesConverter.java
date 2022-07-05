package com.example.pokemonapi.mappers;

import com.example.pokemonapi.models.PokemonMove;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ListOfMovesConverter extends AbstractConverter<List<PokemonMove>, List<String>> {

    @Override
    protected List<String> convert(List<PokemonMove> pokemonMoves) {
        return pokemonMoves
                .stream()
                .map(p->p.getMove().getName())
                .collect(Collectors.toList());
    }
}
