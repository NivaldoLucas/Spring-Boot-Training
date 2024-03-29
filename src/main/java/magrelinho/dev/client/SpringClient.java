package magrelinho.dev.client;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import magrelinho.dev.domain.Anime;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);



        log.info(object);
        //GetForObject para Arrays
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        //GetForObject para Listas diretamente
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<List<Anime>>() {});

        log.info(exchange.getBody());

        
        // Anime cowboy = Anime.builder().name("cowboy").build();
        // Anime cowboySaved = new RestTemplate().postForObject("http://localhost:8080/animes/", cowboy, Anime.class);
        // log.info("Saved anime {}", cowboySaved);
        
        Anime pokemon = Anime.builder().name("Pokemon Fire Red").build();
        ResponseEntity<Anime> pokemonSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(pokemon, createJsonHeader()),
                Anime.class);

        log.info("Saved anime {}", pokemonSaved);



        Anime animeToBeUpdated = pokemonSaved.getBody();
        animeToBeUpdated.setName("pokemon Leaf Green");

        ResponseEntity<Void> pokemonUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);

        log.info(pokemonUpdated);


        ResponseEntity<Void> pokemonDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());

        log.info(pokemonUpdated);



        


    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        return httpHeaders;
    }
}
