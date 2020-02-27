package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.Match;
import cat.udl.eps.softarch.geolearning.domain.MatchResult;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends PagingAndSortingRepository<MatchResult, Integer> {
    /**
     * Find a list of Match results
     */
    List<MatchResult> findByMatch(@Param("match") Match match);
    /**
     * Find a Match by id
     * @return
     */

    Optional<MatchResult> findById(@Param("Id") Integer id);
}
