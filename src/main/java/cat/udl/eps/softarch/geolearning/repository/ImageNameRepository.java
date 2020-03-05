package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.Game;
import cat.udl.eps.softarch.geolearning.domain.ImageName;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestResource
public interface ImageNameRepository extends PagingAndSortingRepository<ImageName, Integer> {

	/* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
	   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
	   *
	   * Additional methods following the syntax defined in
	   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	   */

    //Optional<ImageName> findById(@Param("id") Integer id);
    
}
