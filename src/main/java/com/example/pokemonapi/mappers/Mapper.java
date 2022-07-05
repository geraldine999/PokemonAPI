package com.example.pokemonapi.mappers;


import com.example.pokemonapi.dtos.PokemonDtoBasicResponse;
import com.example.pokemonapi.dtos.PokemonDtoFullResponse;
import com.example.pokemonapi.models.Pokemon;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final org.modelmapper.ModelMapper modelMapper;


    @Autowired
    public Mapper(org.modelmapper.ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        TypeMap<Pokemon, PokemonDtoBasicResponse> propertyMapper =
                this.modelMapper.createTypeMap(Pokemon.class, PokemonDtoBasicResponse.class);

        propertyMapper.addMappings(mapper -> mapper.using(new ListOfAbilitiesConverter())
                .map(Pokemon::getAbilities, PokemonDtoBasicResponse::setAbilitiesNames));

        propertyMapper.addMappings(mapper -> mapper.using(new ListOfTypesConverter())
                .map(Pokemon::getTypes, PokemonDtoBasicResponse::setTypesNames));


       TypeMap<Pokemon, PokemonDtoFullResponse> propertyMapper2 =
                this.modelMapper.createTypeMap(Pokemon.class, PokemonDtoFullResponse.class);

        propertyMapper2.addMappings(mapper -> mapper.using(new ListOfMovesConverter())
                .map(Pokemon::getMoves, PokemonDtoFullResponse::setMovesNames));


    }


    public PokemonDtoFullResponse pokemonToFullDtoResponse(Pokemon pokemon){
        PokemonDtoBasicResponse basicResponse = pokemonToBasicDtoResponse(pokemon);

        PokemonDtoFullResponse fullResponse =modelMapper.map(pokemon, PokemonDtoFullResponse.class);
        fullResponse.setBasicInfo(basicResponse);

        return fullResponse;
    }

    public PokemonDtoBasicResponse pokemonToBasicDtoResponse(Pokemon pokemon){
        return modelMapper.map(pokemon, PokemonDtoBasicResponse.class);
    }




}
