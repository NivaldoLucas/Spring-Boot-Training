package magrelinho.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import magrelinho.dev.domain.Anime;
import magrelinho.dev.requests.AnimePostRequestBody;
import magrelinho.dev.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);

} 