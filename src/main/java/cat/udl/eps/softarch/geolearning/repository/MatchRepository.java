package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.domain.Game;
import cat.udl.eps.softarch.geolearning.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestController
public interface MatchRepository extends PagingAndSortingRepository<Match, Integer> {

    /**
     * Find a Match by id
     * @return
     */

    Optional<Match> findById(@Param("id") Integer id);

    Optional<Match> findByName(@Param("name") String name);

    List<Match> findByContentCreatorContaining(@Param("contentCreator")ContentCreator creator);

    List<Match> findByGamesContaining(@Param("games") Game game);

    boolean existsByName(@Param("name") String name);
}
