package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.Match;
import cat.udl.eps.softarch.geolearning.domain.Player;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PlayerRepository extends PagingAndSortingRepository<Player, String> {

  /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
   *
   * Additional methods following the syntax defined in
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
   */

  Player findByEmail(String email);

  List<Player> findByUsernameContaining(@Param("text") String text);

  List<Player> findByPlayedMatchesContaining(@Param("playedMatches") Match match);
}
