package io.javabrains.ratingsdataservice.resources;

import static java.util.Arrays.asList;

import io.javabrains.ratingsdataservice.models.Rating;
import io.javabrains.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingsResource
{
  @RequestMapping("/{movieId}")
  public Rating getRating(@PathVariable("movieId") String movieId)
  {
    return new Rating(movieId, 4);
  }

  @RequestMapping("/users/{userId}")
  public UserRating getRatingForUser(@PathVariable("userId") String userId)
  {
    return new UserRating(asList(new Rating("1", 4), new Rating("2", 5)));
  }
}
