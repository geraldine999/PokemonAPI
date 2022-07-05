package com.example.pokemonapi.mappers;

import com.example.pokemonapi.models.PokemonAbility;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class ListOfAbilitiesConverter extends AbstractConverter<List<PokemonAbility>, List<String>> {

    @Override
    protected List<String> convert(List<PokemonAbility> pokemonAbilities) {
        return pokemonAbilities
                .stream()
                .map(p->p.getAbility().getName())
                .collect(Collectors.toList());

    }
}
