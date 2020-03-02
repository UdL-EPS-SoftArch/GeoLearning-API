package cat.udl.eps.softarch.geolearning.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Match extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int rating;
    private String name, description;

    @ManyToMany
    private Set<Player> players = new HashSet<Player>();

    @ManyToOne
    private ContentCreator contentCreator;

}
