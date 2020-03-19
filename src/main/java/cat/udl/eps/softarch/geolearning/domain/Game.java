package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Game")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public abstract class Game extends UriEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank
	@Length(min = 10, max = 250)
	private String instructions;

	@Override
	public Integer getId() {
		return id;
	}
}
