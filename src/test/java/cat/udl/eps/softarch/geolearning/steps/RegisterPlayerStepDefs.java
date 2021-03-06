package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.Player;
import cat.udl.eps.softarch.geolearning.domain.User;
import cat.udl.eps.softarch.geolearning.repository.PlayerRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterPlayerStepDefs {

  @Autowired
  private StepDefs stepDefs;

  @Autowired
  private PlayerRepository playerRepository;

  @Given("^There is no registered player with username \"([^\"]*)\"$")
  public void thereIsNoRegisteredUserWithUsername(String player) {
    Assert.assertFalse("player \""
                    +  player + "\"shouldn't exist",
                    playerRepository.existsById(player));
  }

  @Given("^There is a registered player with username \"([^\"]*)\" and password \"([^\"]*)\" and email \"([^\"]*)\"$")
  public void thereIsARegisteredUserWithUsernameAndPasswordAndEmail(String username, String password, String email) {
    if (!playerRepository.existsById(username)) {
      Player player = new Player();
      player.setEmail(email);
      player.setUsername(username);
      player.setPassword(password);
      player.encodePassword();
      playerRepository.save(player);
    }
  }

  @When("^I register a new player with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void iRegisterANewUserWithUsernameEmailAndPassword(String username, String email, String password) throws Throwable {
    Player player = new Player();
    player.setUsername(username);
    player.setEmail(email);

    stepDefs.result = stepDefs.mockMvc.perform(
            post("/players")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject(
                            stepDefs.mapper.writeValueAsString(player)
                    ).put("password", password).toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print());
  }

  @And("^It has been created a player with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
  public void itHasBeenCreatedAUserWithUsername(String username, String email) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/players/{username}", username)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(jsonPath("$.email", is(email)))
            .andExpect(jsonPath("$.password").doesNotExist());
  }

  @And("^It has not been created a player with username \"([^\"]*)\"$")
  public void itHasNotBeenCreatedAUserWithUsername(String username) throws Throwable {
    stepDefs.result = stepDefs.mockMvc.perform(
            get("/players/{username}", username)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
            .andExpect(status().isNotFound());
  }
}
