package com.example.pokemonapi.mappers;
import com.example.pokemonapi.models.PokemonType;
import org.modelmapper.AbstractConverter;


import java.util.List;
import java.util.stream.Collectors;

public class ListOfTypesConverter extends AbstractConverter<List<PokemonType>, List<String>> {


    @Override
    protected List<String> convert(List<PokemonType> pokemonTypes) {
        return pokemonTypes
                .stream()
                .map(p->p.getType().getName())
                .collect(Collectors.toList());
    }
}
