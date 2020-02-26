package cat.udl.eps.softarch.geolearning.config;

//import cat.udl.eps.softarch.geolearning.domain.Player;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.domain.User;
//import cat.udl.eps.softarch.geolearning.repository.PlayerRepository;
import cat.udl.eps.softarch.geolearning.repository.ContentCreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Value("${default-password}")
    String defaultPassword;

    @Autowired
    BasicUserDetailsService basicUserDetailsService;

  /*
  @Autowired
  PlayerRepository playerRepository;
  */

    @Autowired
    ContentCreatorRepository contentCreatorRepository;


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(basicUserDetailsService)
                .passwordEncoder(User.passwordEncoder);

    /*
        // Sample player
    if (!playerRepository.existsById("demoP")) {
      Player player = new Player();
      player.setEmail("demoP@geoLearning.game");
      player.setUsername("demoP");
      player.setPassword(defaultPassword);
      player.encodePassword();
      playerRepository.save(player);
    }

    // Sample player
    if (!playerRepository.existsById("demoP1")) {
      Player player = new Player();
      player.setEmail("demoP1@geoLearning.game");
      player.setUsername("demoP1");
      player.setPassword(defaultPassword);
      player.encodePassword();
      playerRepository.save(player);
    }

     */

        // Sample Tournament Master
        if (!contentCreatorRepository.existsById("demoCC")) {
            ContentCreator admin = new ContentCreator();
            admin.setEmail("demoCC@geoLearning.game");
            admin.setUsername("demoCC");
            admin.setPassword(defaultPassword);
            admin.encodePassword();
            contentCreatorRepository.save(admin);
        }
    }
}
