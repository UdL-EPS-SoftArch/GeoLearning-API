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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteMatchStepDefs{
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


        @When("I delete match with name {string} and description {string} with rating {int}")
    public void iDeleteAMatchtWithNameDescriptionAndRate(String name, String description , Integer rating) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/match/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    /*@And("^It has not been deleted a match with name \"([^\"]*)\"$")
    public void itHasNotBeenDeletedAMatchWithId(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/match/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().is2xxSuccessful());
    }*/


    @And("^It does not exist a match with name \"([^\"]*)\"$")
    public void itDoesNotExistAMatchWithName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/match/{name}", name)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}



