package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.ImageImage;
import cat.udl.eps.softarch.geolearning.domain.ImageOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ImageOptionRepository extends PagingAndSortingRepository<ImageOption, Integer> {

    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
	   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
	   *
	   * Additional methods following the syntax defined in
	   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	   */


	//Optional<ImageOption> findByInstructions(@Param("instructions") String instructions);
	ImageOption findByInstructions(@Param("instructions") String instructions);



}
