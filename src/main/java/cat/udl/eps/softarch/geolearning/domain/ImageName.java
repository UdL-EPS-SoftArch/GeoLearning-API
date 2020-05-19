package cat.udl.eps.softarch.geolearning.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageName extends Game {

    @OneToMany(mappedBy = "imageName" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageNameQuestion> questions;

    @Column
    @NotNull
    private Boolean isWrite = false;

}
