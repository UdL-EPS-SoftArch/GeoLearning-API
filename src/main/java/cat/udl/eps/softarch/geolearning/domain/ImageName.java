package cat.udl.eps.softarch.geolearning.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageName extends Game {

    @OneToMany(mappedBy = "imageName" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageNameQuestion> questions;

    @Column
    @NotNull
    private Boolean isWrite = false;

    @Override
    public String toString(){
        return "ImageName [imageNameId=" + getId() + ", questions=" + questions.toString()
                + ", isWrite=" + isWrite + "]";
    }
}
