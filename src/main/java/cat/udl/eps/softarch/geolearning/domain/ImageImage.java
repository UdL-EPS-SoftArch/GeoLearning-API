package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageImage extends Game{

    @OneToMany(mappedBy = "imageImage" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageImageQuestion> questions = new ArrayList<>();

}
