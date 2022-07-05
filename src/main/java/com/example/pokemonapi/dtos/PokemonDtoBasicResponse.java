package com.example.pokemonapi.dtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "Pokemon Basic Info")
public class PokemonDtoBasicResponse {

    @Schema(example = "ivysaur")
    private String name;

    @Schema(example = "[\n" +
            "      \"grass\"]")
    private List<String> typesNames;

    @Schema(example = "130")
    private int weight;

    @Schema(example = "[\n" +
            "      \"overgrow\",\n" +
            "      \"chlorophyll\"\n" +
            "    ]")
    private List<String> abilitiesNames;

}
