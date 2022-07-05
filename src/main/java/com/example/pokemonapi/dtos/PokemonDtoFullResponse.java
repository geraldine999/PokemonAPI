package com.example.pokemonapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "Pokemon Full Info")
public class PokemonDtoFullResponse {

    private PokemonDtoBasicResponse basicInfo;

    @Schema(example = "[\n" +
            "      \"cut\",\n" +
            "      \"bind\"\n" +
            "    ]")
    private List<String> movesNames;

    @Schema(example = "[\n" +
            "      \"Le gusta comer\",\n" +
            "      \"A menudo se duerme\"\n" +
            "    ]")
    private List<String> descriptions;
}
