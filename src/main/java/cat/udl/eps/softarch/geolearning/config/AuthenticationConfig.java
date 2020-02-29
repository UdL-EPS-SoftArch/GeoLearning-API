package cat.udl.eps.softarch.geolearning.config;

import cat.udl.eps.softarch.geolearning.domain.Player;
import cat.udl.eps.softarch.geolearning.domain.User;
import cat.udl.eps.softarch.geolearning.repository.ContentCreatorRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  final BasicUserDetailsService basicUserDetailsService;
  final UserRepository userRepository;
  final ContentCreatorRepository contentCreatorRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository,
                              ContentCreatorRepository contentCreatorRepository) {
    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.contentCreatorRepository = contentCreatorRepository;
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(basicUserDetailsService)
            .passwordEncoder(User.passwordEncoder);

    // Sample Player
    if (!userRepository.existsById("demo")) {
      Player player = new Player();
      player.setEmail("demo@sample.app");
      player.setUsername("demo");
      player.setPassword(defaultPassword);
      player.encodePassword();
      userRepository.save(player);
    }

    // Sample Content Creator
    if (!userRepository.existsById("demo")) {
        User user = new ContentCreator();
        user.setEmail("demo@sample.app");
        user.setUsername("demo");
        user.setPassword(defaultPassword);
        user.encodePassword();
        userRepository.save(user);
    }
  }
}
