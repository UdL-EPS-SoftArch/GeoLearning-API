package cat.udl.eps.softarch.geolearning.config;

import cat.udl.eps.softarch.geolearning.domain.*;
import cat.udl.eps.softarch.geolearning.repository.ContentCreatorRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageNameRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  final BasicUserDetailsService basicUserDetailsService;
  final UserRepository userRepository;
  final ContentCreatorRepository contentCreatorRepository;
  final ImageNameRepository imageNameRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository,
                              ContentCreatorRepository contentCreatorRepository, ImageNameRepository imageNameRepository) {
    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.contentCreatorRepository = contentCreatorRepository;
    this.imageNameRepository = imageNameRepository;
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
    if (!userRepository.existsById("creator")) {
      User user = new ContentCreator();
      user.setEmail("creator@sample.app");
      user.setUsername("creator");
      user.setPassword(defaultPassword);
      user.encodePassword();
      userRepository.save(user);
    }

  }
}