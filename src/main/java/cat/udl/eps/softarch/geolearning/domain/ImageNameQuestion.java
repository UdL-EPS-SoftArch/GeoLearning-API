package cat.udl.eps.softarch.geolearning.domain;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.ImageProducer;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageNameQuestion extends UriEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inwq_id;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private ImageName imageName;


    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String image;

    @Column
    @NotBlank
    private String solution;

    @Override
    public Integer getId() {
        return inwq_id;
    }

    @Override
    public String toString(){
        return "ImageNameQuestion [imageNameQuestionId=" + inwq_id + ", image=" + image
                + ", solution=" + solution + "]";
    }
}
