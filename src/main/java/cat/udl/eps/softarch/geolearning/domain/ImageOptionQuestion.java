package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cat.udl.eps.softarch.geolearning.repository.ImageOptionRepository;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageOptionQuestion extends UriEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ioq_id;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    ImageOption imageOption;

    @Column(length = 5 * 1024 * 1024) // 5MB
    @Size(max = 5 * 1024 * 1024) // 5MB
    @NotBlank
    private String image;

    @Column
    @NotBlank
    private String solution;

    @Column
    @NotBlank
    private String optionA;

    @Column
    @NotBlank
    private String optionB;

    @Column
    @NotBlank
    private String optionC;

    @Column
    @NotBlank
    private String optionD;

    @Column
    @NotBlank
    private String optionE;

    @Override
    public Integer getId() {
        return ioq_id;
    }
}
