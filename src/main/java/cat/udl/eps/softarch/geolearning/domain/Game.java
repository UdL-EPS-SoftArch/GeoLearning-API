package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Game")
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Game extends UriEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Length(min = 10, max = 250)
	private String instructions;

	@ManyToOne
	@JsonIdentityReference(alwaysAsId = true)
	private ContentCreator creator;

	@Override
	public Integer getId() {
		return id;
	}


}
