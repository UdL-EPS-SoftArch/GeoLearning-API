package cat.udl.eps.softarch.geolearning.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageOptions extends Game {

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageOptionsQuestions> questions = new ArrayList<>();
}
