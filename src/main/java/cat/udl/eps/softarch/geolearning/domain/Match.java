package cat.udl.eps.softarch.geolearning.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Match extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @NotNull
    private Integer rating;

    private String description;

    @ManyToMany
    //@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Game> games;

    @ManyToOne
    private ContentCreator contentCreator;

    public Match(){
        super();
    }

    public Match(String name, String description, Integer rating){
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

}
