package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageName;
import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonObject;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;
    private GetImageNameGameStepDefs getImageNameGameStepDefs;


    @Autowired
    private ImageNameRepository imageNameRepository;
    private ImageNameWriteQuestionRepository imageNameWriteQuestionRepository;
    private ImageNameWriteQuestion imageNameWriteQuestion1;
    private ImageNameWriteQuestion imageNameWriteQuestion2;


    private ImageName imageName;
    private int id;
    private String newResourceUri;
    private String questionsUri;


    @When("^I delete the previous ImageName")
    public void iDeleteThePreviousImageName() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        jsonObject = (JsonObject) parser.parse(response);
        jsonObject = (JsonObject) jsonObject.get("_links");
        jsonObject = (JsonObject) jsonObject.get("questions");
        questionsUri = jsonObject.get("href").getAsString();

        newResourceUri = getImageNameGameStepDefs.newResourceUri;
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^Previous ImageName is still in the database")
    public void previousImageNameIsStillInTheDatabase() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @And("^Previous ImageName has been deleted")
    public void previousImageNameHasBeenDeleted() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());

        stepDefs.result = stepDefs.mockMvc.perform(
                get(questionsUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
