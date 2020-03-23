package cat.udl.eps.softarch.geolearning.domain;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageNameWriteQuestion extends UriEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inwq_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageName_id", nullable = false)
    ImageName imageName;


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
}
