package cat.udl.eps.softarch.geolearning.domain;

import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImagenName extends Game {

	Map<Integer, String> images;
		
}
