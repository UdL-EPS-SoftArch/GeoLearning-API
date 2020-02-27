package cat.udl.eps.softarch.geolearning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Write extends Game{
	
	@Id
	@NotBlank
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String image;
	
	@Column
	private String resultat;

}
