package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Game")
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Game extends UriEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotBlank
	private Long id;
	
	@NotBlank
	@Length(min = 10, max = 250)
	private String instructions;

	@Override
	public Long getId() {
		return id;
	}
}
