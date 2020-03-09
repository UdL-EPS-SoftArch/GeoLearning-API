package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface MatchRepository extends PagingAndSortingRepository<Match, Integer> {

    /**
     * Find a Match by id
     * @return
     */

    Optional<Match> findById(@Param("Id") Integer id);
}
