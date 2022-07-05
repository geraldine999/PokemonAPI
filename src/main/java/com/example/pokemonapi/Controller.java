package com.example.pokemonapi;

import com.example.pokemonapi.dtos.PokemonDtoBasicResponse;
import com.example.pokemonapi.dtos.PokemonDtoFullResponse;
import com.example.pokemonapi.mappers.Mapper;
import com.example.pokemonapi.models.*;
import com.example.pokemonapi.properties.FilterResultProperties;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.retry.Retry;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(path = "/pokemon")
public class Controller {

    private final WebClient webClient;
    private final Mapper mapper;
    private final FilterResultProperties filterResultProperties;

    @Autowired
    public Controller(WebClient webClient, Mapper mapper, FilterResultProperties filterResultProperties) {
        this.webClient = webClient;
        this.mapper = mapper;
        this.filterResultProperties = filterResultProperties;
    }

    private static final String URL = "https://pokeapi.co/api/v2/pokemon";

    private final Retry<?> fixedRetry = Retry.anyOf(WebClientResponseException.class)
            .fixedBackoff(Duration.ofSeconds(2))
            .retryMax(3)
            .doOnRetry(exception-> log.error("Exception is {}", exception));


    @Operation(summary= "Get Pokemons", description = "Returns a list of pokemons and their" +
            " basic info from 'offset' to 'limit'")
    @GetMapping("get/limit/{limit}/offset/{offset}")
    public List<PokemonDtoBasicResponse> getPokemons(@PathVariable int limit, @PathVariable int offset){

        List<Named> results = getListOfObjects(
                URL + "?limit="+limit+"&"+"offset="+offset, Named.class);


       List<Pokemon> pokemons = results.stream()
               .map(Named::getResults)
               .flatMap(Collection::stream)
                       .map(namedApiResource -> getObject(namedApiResource.getUrl(), Pokemon.class))
               .collect(Collectors.toList());

       return pokemons.stream()
               .map(mapper::pokemonToBasicDtoResponse)
               .collect(Collectors.toList());


    }


    @Operation(summary= "Get Pokemon by name", description = "Returns full info of a certain Pokemon")
    @GetMapping("get/name/{name}")
    public PokemonDtoFullResponse getPokemonByName(@PathVariable String name){

        Pokemon pokemon = getObject(URL+"/"+name, Pokemon.class);

        PokemonDtoFullResponse dto = mapper.pokemonToFullDtoResponse(pokemon);

        dto.setDescriptions(getAPokemonsDescriptions(pokemon));

        return dto;

    }


    private <T> T getObject(String url, Class<T> aClass){
        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(aClass)
                .retryWhen(reactor.util.retry.Retry.withThrowable(fixedRetry))
                .block();
    }

    private <T> List<T> getListOfObjects(String url, Class<T> aClass){
        return webClient.get().uri(url)
                .retrieve()
                .bodyToFlux(aClass)
                .retryWhen(reactor.util.retry.Retry.withThrowable(fixedRetry))
                .collectList()
                .block();
    }

    private List<String> getAPokemonsDescriptions(Pokemon pokemon) {
        List<Stat> stats = getPokemonStats(pokemon);

       List<List<Characteristic>> characteristicsList = stats
                .stream()
                .map(this::getCharacteristicsFromAStat)
                .collect(Collectors.toList());


      characteristicsList =characteristicsList.stream()
              .map(this::filterCharacteristicsDescriptionsByLanguage)
              .collect(Collectors.toList());

        return getDescriptionsFromCharacteristics(characteristicsList);

    }

    private List<Stat> getPokemonStats(Pokemon pokemon) {
        return pokemon.getStats().stream()
                .map(PokemonStat::getStat)
                .map(s-> getObject(s.getUrl(), Stat.class))
                .collect(Collectors.toList());
    }

    private List<Characteristic> getCharacteristicsFromAStat(Stat stat){
        return stat.getCharacteristics().stream()
                .map(c-> getObject(c.getUrl(), Characteristic.class))
                .collect(Collectors.toList());
    }

    private List<Characteristic> filterCharacteristicsDescriptionsByLanguage(List<Characteristic> characteristics){

        for (Characteristic c: characteristics) {
            List<Description> descriptions = c.getDescriptions();
            descriptions.removeIf(d -> !d.getLanguage().getName()
                    .equals(filterResultProperties.getLanguageDescriptions()));
            c.setDescriptions(descriptions);
        }
        return characteristics;
    }

    private List<String> getDescriptionsFromCharacteristics(List<List<Characteristic>> characteristics) {
        List<String> descriptions = new ArrayList<>();
        for (List<Characteristic> characteristicList: characteristics) {
            for (Characteristic c: characteristicList) {
               List<String> descriptionList= c.getDescriptions()
                       .stream()
                       .map(Description::getDescription)
                       .collect(Collectors.toList());
                descriptions.addAll(descriptionList);
            }
        }

        return descriptions;

    }


}
