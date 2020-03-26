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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateMatchStepDefs {
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

    @And("There is no registered match with name {string}")
    public void noMatchExisting(String name){
        Assert.assertFalse(matchRepository.existsByName(name));
    }

    @When("I register a new match with name {string} and description {string} with rating {int}")
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

    @And("It has been created a match with and name {string} and description {string} with rating {int}")
    public void checkRegisteredMatch(String name, String description, Integer rating) throws Exception{

        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate())
        ).andDo(print())
        .andExpect(jsonPath("$.name", is(name)))
        .andExpect(jsonPath("$.description", is(description)))
        .andExpect(jsonPath("$.rating", is(rating)))
        //.andExpect(jsonPath("$.games", is(games)))
        //.andExpect(jsonPath("$.contentCreator", is(creatorRepository.findByEmail("creator@sample.app"))))
        ;
    }

    @Given("There is a created match with name {string} and description {string} with rating {int}")
    public void givenCreatedMatch(String name, String description, Integer rating){
        Match match = new Match(name, description, rating);
        matchRepository.save(match);
    }

    @And("There is a registered match with name {string}")
    public void matchNameRegistered(String name){
        Assert.assertTrue(matchRepository.existsByName(name));
    }

    @And("I cannot create a match with name {string} and description {string} with rating {int}")
    public void checkNameRegistered(String name, String description, Integer rating) throws Exception{
        Assert.assertNull(newResourceUri);
    }

    @And("I cannot create a match with blank name")
    public void checkNullName() throws Exception{
        Assert.assertNull(newResourceUri);
    }

    @And("I cannot create a match with name {string} and description {string} with rating {int}, cause already exists")
    public void checkUniqueName(String name, String description, Integer rating) throws Exception{
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description",not(description)))
                .andExpect(jsonPath("$.rating",not(rating)));
    }

    @When("I register a new match with name {string} and description {string} with no rating")
    public void registerNoRating(String name, String description) throws Exception{
        Match match = new Match();
        match.setName(name);
        match.setDescription(description);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(match))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("I cannot create a match with name {string} and description {string} with no rating")
    public void checkNoRating(String name, String description){
        Assert.assertNull(newResourceUri);
    }
}
