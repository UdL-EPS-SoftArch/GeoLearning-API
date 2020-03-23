package cat.udl.eps.softarch.geolearning.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "GeoLearningContentCreator")
@Data
@EqualsAndHashCode(callSuper = true)
public class ContentCreator extends User {
    @Override
    @JsonValue(value = false)
    @JsonProperty(access = Access.READ_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("CONTENT_CREATOR");
    }
}
