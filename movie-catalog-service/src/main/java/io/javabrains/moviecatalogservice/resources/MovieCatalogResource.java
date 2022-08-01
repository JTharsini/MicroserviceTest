package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/catalogs")
public class MovieCatalogResource
{
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private WebClient.Builder builder;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
  {
    UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userId, UserRating.class);
    return userRating.getRatings().stream().map(rating -> {
      // this call is synchronous. wait until the rest template gives the output
      Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
      assert movie != null;
      return new CatalogItem(movie.getName(), "Romantic", rating.getRating());
    }).collect(toList());
  }

  @RequestMapping("/v2/{userId}")
  public List<CatalogItem> getCatalogAsynchronously(@PathVariable("userId") String userId)
  {
    UserRating rating =
      builder.build().get().uri("http://ratings-data-service/ratings/users/" + userId).retrieve().bodyToMono(UserRating.class)
        .block();
    assert rating != null;
    return rating.getRatings().stream().map(r -> {
      // since http method is get, after builder.build() get
      // bodyToMono - Mono means this will be returned at some point. not immediately
      // but here no way. need to wait until result come. then block()
      Movie movie =
        builder.build().get().uri("http://movie-info-service/movies/" + r.getMovieId()).retrieve().bodyToMono(Movie.class)
          .block();
      assert movie != null;
      return new CatalogItem(movie.getName(), "Romantic", r.getRating());
    }).collect(toList());
  }
}
