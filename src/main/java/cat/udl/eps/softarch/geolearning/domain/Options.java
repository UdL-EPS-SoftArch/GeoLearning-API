package cat.udl.eps.softarch.geolearning.domain;

import java.util.List;

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
public class Options extends Game {

	@Id
	@NotBlank
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String image;
	
	@Column
	private List<String> results;
	
	@Column
	private String resultOk;
}
