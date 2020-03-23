package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.ImageWrite;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ImageWriteRepository extends PagingAndSortingRepository<ImageWrite, Integer> {

	/* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
	   * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
	   *
	   * Additional methods following the syntax defined in
	   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	   */
}
