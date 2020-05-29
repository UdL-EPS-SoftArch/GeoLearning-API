package cat.udl.eps.softarch.geolearning.domain;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageImageQuestion extends UriEntity<Integer>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iiq_id;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    ImageImage imageImage;

    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String image;

    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String solution;

    @Override
    public Integer getId() {
        return iiq_id;
    }

    @Override
    public String toString(){
        return "ImageNameQuestion [imageImageQuestionId=" + iiq_id + ", image=" + image
                + ", solution=" + solution + "]";
    }
}
