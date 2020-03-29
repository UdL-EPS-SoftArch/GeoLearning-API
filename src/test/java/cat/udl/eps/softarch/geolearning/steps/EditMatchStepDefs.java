package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.*;
import cat.udl.eps.softarch.geolearning.repository.*;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.data.web.JsonPath;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EditMatchStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ContentCreatorRepository creatorRepository;

    @Autowired
    private ImageImageRepository imageRepository;

    @Autowired
    private ImageOptionRepository imageOptionRepository;

    private String newResourceUri;
    private Integer postedId;

    @When("I create a new match with name {string} and description {string} with rating {int}")
    public void registerMatch(String name, String description, Integer rating) throws Exception {
        Match match = new Match();
        match.setName(name);
        match.setDescription(description);
        match.setRating(rating);

        //S'han d'incorporar els objectes de les relacions del domini o s'estableixen en un altre test features¿?
        /*ImageImage image = new ImageImage();
        image.setInstructions("Primer joc: ImageImage");
        imageRepository.save(image);
        List<Game> games = new ArrayList<>();
        games.add(image);
        match.setGames(games);
        ContentCreator creator = creatorRepository.findByEmail("creator@sample.app");
        creatorRepository.save(creator);
        match.setContentCreator(creator);
        JSONObject matchJSON = new JSONObject();
        matchJSON.put("id", match.getId());
        matchJSON.put("name", match.getName());
        matchJSON.put("description", match.getDescription());
        matchJSON.put("games", new Gson().toJson(games));
        matchJSON.put("contentCreator", new Gson().toJson(creator));
        System.out.println("JsonObject: " + matchJSON.toString());*/

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(match))
//                .content(matchJSON.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I edit match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iEditTournamentWithNameLevelAndGame(String name, String description) throws Throwable {
        JSONObject Match = new JSONObject();
        Match.put("name", name);
        Match.put("description", description);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/matches/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has been edited a match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void itHasBeenEditedAMatchWithNameDescription(String name, String description) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(get("/matches/{name}", name)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It has not been edited a match with name \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void itHasNotBeenEditedAMatchWithNameDescription(String name, String description) throws Throwable {
        stepDefs.result = stepDefs.mockMvc
                .perform(
                        get("/games/{name}", name,description)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit match with name \"([^\"]*)\" and new rating \"([^\"]*)\"$")
    public void iEditMatchWithNameAndNewRating(String name, int rating) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject Match = new JSONObject();
        Match.put("rating", rating);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/matches/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit match with name \"([^\"]*)\" and new name \"([^\"]*)\"$")
    public void iEditMatchWithNameAndNewName(String name, String name2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject Match = new JSONObject();
        Match.put("name", name2);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/matches/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I edit match with name \"([^\"]*)\" and new description \"([^\"]*)\"$")
    public void iEditMatchWithNameAndNewDescription(String name, String description) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        JSONObject Match = new JSONObject();
        Match.put("description", description);
        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/matches/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Match.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^It does not exist a match with name \"([^\"]*)\"$")
    public void itDoesNotExistAMatchWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/match/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
