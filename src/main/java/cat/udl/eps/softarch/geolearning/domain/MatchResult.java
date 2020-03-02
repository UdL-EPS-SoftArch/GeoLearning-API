package cat.udl.eps.softarch.geolearning.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MatchResult extends UriEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int result;
    private float time;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Player player;
}
