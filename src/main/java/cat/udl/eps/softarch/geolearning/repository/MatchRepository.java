package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface MatchRepository extends PagingAndSortingRepository<Match, String> {
    /**
     * Find a list of Match results
     */
    //List<MatchResult> findByMatch(@Param("match") Match match);
    /**
     * Find a Match by id
     */
    Match findById(@Param("id") Integer id);
}
