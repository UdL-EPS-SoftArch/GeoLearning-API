package cat.udl.eps.softarch.geolearning.config;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.domain.User;
import cat.udl.eps.softarch.geolearning.repository.ContentCreatorRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Value("${default-password}")
    String defaultPassword;

    @Autowired BasicUserDetailsService basicUserDetailsService;
    @Autowired UserRepository userRepository;
    @Autowired ContentCreatorRepository contentCreatorRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(basicUserDetailsService)
                .passwordEncoder(User.passwordEncoder);

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
