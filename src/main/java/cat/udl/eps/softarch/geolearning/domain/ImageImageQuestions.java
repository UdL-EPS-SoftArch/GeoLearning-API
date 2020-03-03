package cat.udl.eps.softarch.geolearning.domain;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "ImageImageQuestions")
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageImageQuestions extends UriEntity<Long>{
   
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank
    private Long id;

    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String image;

    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String solution;
}
