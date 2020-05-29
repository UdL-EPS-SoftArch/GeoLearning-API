package cat.udl.eps.softarch.geolearning.repository;


import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.udl.eps.softarch.geolearning.domain.Game;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface GameRepository extends PagingAndSortingRepository<Game, Integer> {

	/* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
	   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
	   *
	   * Additional methods following the syntax defined in
	   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	   */

	Optional<Game> findById(@Param("id") Integer id);
	List<Game> findGamesByCreatorUriContaining(String uri);
	List<Game> findGamesByCreatorUsernameContaining(@Param("id") String username);
}
