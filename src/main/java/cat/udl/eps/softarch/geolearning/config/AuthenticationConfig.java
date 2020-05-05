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

    ImageName imageName = new ImageName();
    imageName.setInstructions("Match name and image");
    ImageNameQuestion imageNameQuestion1 = new ImageNameQuestion();
    imageNameQuestion1.setImage("https://upload.wikimedia.org/wikipedia/commons/d/d5/Flag_of_Spain_%28WFB_2000%29.jpg");
    imageNameQuestion1.setSolution("spain");
    imageNameQuestion1.setImageName(imageName);
    ImageNameQuestion imageNameQuestion2 = new ImageNameQuestion();
    imageNameQuestion2.setImage("https://upload.wikimedia.org/wikipedia/commons/0/03/Flag_of_Italy.svg");
    imageNameQuestion2.setSolution("italy");
    imageNameQuestion2.setImageName(imageName);
    List<ImageNameQuestion> questions = new ArrayList<>();
    questions.add(imageNameQuestion1);
    questions.add(imageNameQuestion2);
    imageName.setQuestions(questions);
    imageNameRepository.save(imageName);

    ImageName imageName1 = new ImageName();
    imageName1.setInstructions("Match name and image");
    ImageNameQuestion imageNameQuestion3 = new ImageNameQuestion();
    imageNameQuestion3.setImage("https://cdn.webshopapp.com/shops/94414/files/52385798/flag-of-japan.jpg");
    imageNameQuestion3.setSolution("japan");
    imageNameQuestion3.setImageName(imageName1);
    List<ImageNameQuestion> questions1 = new ArrayList<>();
    questions1.add(imageNameQuestion3);
    imageName1.setQuestions(questions1);
    imageNameRepository.save(imageName1);

  }
}