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
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;


    @Autowired
    private GetImageNameGameStepDefs getImageNameGameStepDefs;
    private ResultActions result;

    private String questionsUri;

    @And("^There is an ImageName game with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void thereIsAnImageNameGameWithInstructionsAndQuestions (String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        getImageNameGameStepDefs.thereIsAnImageNameWithInstructionsAndQuestions(instructions, image1, response1, image2, response2);

        result = stepDefs.mockMvc.perform(
            get(getImageNameGameStepDefs.newResourceUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
            .andDo(print())
            .andExpect(status().isOk());

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        String response = this.result.andReturn().getResponse().getContentAsString();
        jsonObject = (JsonObject) parser.parse(response);
        jsonObject = (JsonObject) jsonObject.get("_links");
        jsonObject = (JsonObject) jsonObject.get("questions");
        questionsUri = jsonObject.get("href").getAsString();
    }


    @When("^I delete the previous ImageName")
    public void iDeleteThePreviousImageName() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(getImageNameGameStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^Previous ImageName and its questions have been deleted")
    public void previousImageNameHasBeenDeleted() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameGameStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());

        stepDefs.mockMvc.perform(
                get(questionsUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
