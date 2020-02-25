package cat.udl.eps.softarch.geolearning.repository;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.swing.text.AbstractDocument;
import java.util.List;

@RepositoryRestResource
public interface ContentCreatorRepository extends PagingAndSortingRepository<ContentCreator, String> {

    /* Interface provides automatically, as defined in https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
     * count, delete, deleteAll, deleteById, existsById, findAll, findAllById, findById, save, saveAll
     *
     * Additional methods following the syntax defined in
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */
    ContentCreator findByEmail(String email);
    ContentCreator findByUsername(String username);
    List<ContentCreator> findByUsernameContaining(@Param("text") String text);
}
