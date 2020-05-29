package cat.udl.eps.softarch.geolearning.handler;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class CreateMatchHandler {
    final Logger logger = LoggerFactory.getLogger(Match.class);

    @HandleBeforeCreate
    public void  handleMatchPreCreate(Match match){
        logger.info("Before creating: {}", match.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username: {}", authentication.getAuthorities());

        match.setContentCreator((ContentCreator)authentication.getPrincipal());
    }
}
