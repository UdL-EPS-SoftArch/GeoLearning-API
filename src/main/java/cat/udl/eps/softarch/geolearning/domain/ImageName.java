package cat.udl.eps.softarch.geolearning.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageName extends Game {

    @OneToMany(mappedBy = "imageName" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageNameWriteQuestion> questions;
}
