package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.GameRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonObject;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.minidev.json.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EditImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameGameStepDefs getImageNameGameStepDefs;
    private ImageNameWriteQuestionRepository imageNameWriteQuestionRepository;
    private GameRepository gameRepository;
    private String newResourceUri;

    @When("^I edit the previous ImageName with instructions \"([^\"]*)\"")
    public void iEditThePreviousImageName(String instructions) throws Throwable {

        JSONObject imageName = new JSONObject();
        imageName.put("instructions", instructions);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameGameStepDefs.newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imageName.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageName has been updated with intructions \"([^\"]*)\"")
    public void iCheckThatImageNameHasBeenUpdated(String instructions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameGameStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));
    }

    @When("^I edit the previous ImageName with question \"([^\"]*)\" and \"([^\"]*)\" and question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iEditPreviousImageNameAndAddQuestions(String image1, String solution1, String image2, String solution2) throws Throwable {

        ImageNameWriteQuestion imageNameWriteQuestion1 = new ImageNameWriteQuestion();
//        imageNameWriteQuestion1.setImageName(getImageNameGameStepDefs.imageName);
        imageNameWriteQuestion1.setImage(image1);
        imageNameWriteQuestion1.setSolution(solution1);
        String question1 = imageNameWriteQuestionRepository.save(imageNameWriteQuestion1).getUri();

        ImageNameWriteQuestion imageNameWriteQuestion2 = new ImageNameWriteQuestion();
//        imageNameWriteQuestion2.setImageName(getImageNameGameStepDefs.imageName);
        imageNameWriteQuestion2.setImage(image2);
        imageNameWriteQuestion2.setSolution(solution2);
        String question2 = imageNameWriteQuestionRepository.save(imageNameWriteQuestion2).getUri();

//        List<ImageNameWriteQuestion> listQuestions = new ArrayList<>();
//        listQuestions.add(imageNameWriteQuestion1);
//        listQuestions.add(imageNameWriteQuestion2);
//
//        JSONArray json = new JSONArray(listQuestions);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("questions",json);

        stepDefs.result = stepDefs.mockMvc.perform(
                post(getImageNameGameStepDefs.newResourceUri + "/questions")
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(question1)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                ).andDo(print());

        stepDefs.result = stepDefs.mockMvc.perform(
                post(getImageNameGameStepDefs.newResourceUri + "/questions")
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(question2)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
            ).andDo(print());
    }

    @And("^ImageName has been updated with question \"([^\"]*)\" and \"([^\"]*)\" and question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iUpdatedPreviousImageNameQuestions(String image1, String solution1, String image2, String solution2) throws Throwable {
        getImageNameGameStepDefs.iRequestThePreviousImageName();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject;
        String response = stepDefs.result.andReturn().getResponse().getContentAsString();
        jsonObject = (JsonObject) parser.parse(response);
        jsonObject = (JsonObject) jsonObject.get("_links");
        jsonObject = (JsonObject) jsonObject.get("questions");
        String questionsUri = jsonObject.get("href").getAsString();

        stepDefs.result = stepDefs.mockMvc.perform(
                get(questionsUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[0].image", is(image1)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[0].solution", is(solution1)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[1].image", is(image2)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[1].solution", is(solution2)));
    }
}
