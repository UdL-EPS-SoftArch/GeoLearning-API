package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageOptionsQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank
    private Long id;

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
}
