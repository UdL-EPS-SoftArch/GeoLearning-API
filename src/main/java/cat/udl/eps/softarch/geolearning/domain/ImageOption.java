package cat.udl.eps.softarch.geolearning.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageOption extends Game {

    @OneToMany(mappedBy = "imageOption" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageOptionQuestion> questions = new ArrayList<>();
}
