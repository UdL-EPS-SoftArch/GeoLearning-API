package cat.udl.eps.softarch.geolearning.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Value("${allowed-origins}")
        String[] allowedOrigins;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/identity").authenticated()
                    .antMatchers(HttpMethod.POST, "/contentCreators").anonymous()
                    .antMatchers(HttpMethod.POST, "/contentCreators/*").denyAll()
                    .antMatchers(HttpMethod.POST, "/players").anonymous()
                    .antMatchers(HttpMethod.POST, "/players/*").denyAll()
                    .antMatchers(HttpMethod.POST, "/games").anonymous()
                    .antMatchers(HttpMethod.POST, "/games/*").denyAll()

                    .antMatchers(HttpMethod.GET, "/imageNames").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageNames/*").authenticated()
                    .antMatchers(HttpMethod.POST, "/imageNames").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.POST, "/imageNames/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.DELETE, "/imageNames").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.DELETE, "/imageNames/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageNames").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageNames/*").hasRole("CONTENTCREATOR")

                    .antMatchers(HttpMethod.POST, "/imageImages").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageImages").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.GET, "/imageImages").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageImages/*").authenticated()

                    .antMatchers(HttpMethod.POST, "/imageOptions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageOptions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.GET, "/imageOptions").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageOptions/*").authenticated()

                    .antMatchers(HttpMethod.GET, "/imageNameQuestions").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageNameQuestions/*").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/imageNameQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.DELETE, "/imageNameQuestions/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.POST, "/imageNameQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.POST, "/imageNameQuestions/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageNameQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageNameQuestions/*").hasRole("CONTENTCREATOR")

                    .antMatchers(HttpMethod.POST, "/imageImageQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageImageQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageImageQuestions/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.GET, "/imageImageQuestions").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageImageQuestions/*").authenticated()

                    .antMatchers(HttpMethod.POST, "/imageOptionQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageOptionQuestions").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.PATCH, "/imageOptionQuestions/*").hasRole("CONTENTCREATOR")
                    .antMatchers(HttpMethod.GET, "/imageOptionQuestions").authenticated()
                    .antMatchers(HttpMethod.GET, "/imageOptionQuestions/*").authenticated()

                    .antMatchers(HttpMethod.POST, "/**/*").authenticated()
                    .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
                    .antMatchers(HttpMethod.PATCH, "/**/*").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic().realmName("demo")
                    .and()
                    .cors()
                    .and()
                    .csrf().disable();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
            corsConfiguration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }
    }


