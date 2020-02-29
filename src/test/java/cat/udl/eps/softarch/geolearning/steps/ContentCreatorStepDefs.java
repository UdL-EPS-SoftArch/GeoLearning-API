package cat.udl.eps.softarch.geolearning.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cat.udl.eps.softarch.geolearning.domain.ContentCreator;
import cat.udl.eps.softarch.geolearning.repository.ContentCreatorRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

public class
ContentCreatorStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ContentCreatorRepository contentCreatorRepository;

    @Given("^There is no registered contentcreator with username \"([^\"]*)\"$")
    public void thereIsNoRegisteredContentcreatorWithUsername(String admin) {
        Assert.assertFalse("Content creator \""
                        + admin + "\"shouldn't exist",
                contentCreatorRepository.existsById(admin));
    }

    @Given("^There is a registered contentcreator with username \"([^\"]*)\" and password \"([^\"]*)\" and email \"([^\"]*)\"$")
    public void thereIsARegisteredContentcreatorWithUsernameAndPasswordAndEmail(String username, String password, String email) {
        if (!contentCreatorRepository.existsById(username)) {
            ContentCreator admin = new ContentCreator();
            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(password);
            admin.encodePassword();
            contentCreatorRepository.save(admin);
        }
    }

    @When("^I register a new contentcreator with username \"([^\"]*)\", email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iRegisterANewContentcreatorWithUsernameEmailAndPassword(String username, String email, String password) throws Throwable {
      ContentCreator admin = new ContentCreator();
        admin.setUsername(username);
        admin.setEmail(email);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/contentCreators")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new JSONObject(
                                stepDefs.mapper.writeValueAsString(admin)
                        ).put("password", password).toString())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been created a contentcreator with username \"([^\"]*)\" and email \"([^\"]*)\", the password is not returned$")
    public void itHasBeenCreatedAContentcreator(String username, String email) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/contentCreators/{username}", username)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @And("^It has not been created a contentcreator with username \"([^\"]*)\"$")
    public void itHasNotBeenCreatedAContentcreatorWithUsername(String username) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/contentCreators/{username}", username)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isNotFound());
    }
}
