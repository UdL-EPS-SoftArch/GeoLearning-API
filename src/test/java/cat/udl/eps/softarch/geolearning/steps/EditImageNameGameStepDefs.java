package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.GameRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonArray;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EditImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameGameStepDefs getImageNameGameStepDefs;

    @Autowired
    private ImageNameWriteQuestionRepository imageNameWriteQuestionRepository;

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

    @And("^ImageName has been updated with instructions \"([^\"]*)\"")
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
        imageNameWriteQuestion1.setImageName(getImageNameGameStepDefs.imageName);
        imageNameWriteQuestion1.setImage(image1);
        imageNameWriteQuestion1.setSolution(solution1);
        String question1 = imageNameWriteQuestionRepository.save(imageNameWriteQuestion1).getUri();

        ImageNameWriteQuestion imageNameWriteQuestion2 = new ImageNameWriteQuestion();
        imageNameWriteQuestion2.setImageName(getImageNameGameStepDefs.imageName);
        imageNameWriteQuestion2.setImage(image2);
        imageNameWriteQuestion2.setSolution(solution2);
        String question2 = imageNameWriteQuestionRepository.save(imageNameWriteQuestion2).getUri();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameGameStepDefs.newResourceUri + "/questions")
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(question1)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameGameStepDefs.newResourceUri + "/questions")
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(question2)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).
                andDo(print());
    }

    @When("^I edit the previous ImageName with question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iEditPreviousImageNameAndAddQuestion(String image, String solution) throws Throwable {

        ImageNameWriteQuestion imageNameWriteQuestion1 = new ImageNameWriteQuestion();
        imageNameWriteQuestion1.setImageName(getImageNameGameStepDefs.imageName);
        imageNameWriteQuestion1.setImage(image);
        imageNameWriteQuestion1.setSolution(solution);
        String question = imageNameWriteQuestionRepository.save(imageNameWriteQuestion1).getUri();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameGameStepDefs.newResourceUri + "/questions")
                        .contentType(RestMediaTypes.TEXT_URI_LIST)
                        .content(question)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageName has been updated with question with \"([^\"]*)\" and \"([^\"]*)\" and question with \"([^\"]*)\" and \"([^\"]*)\"")
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

    @And("^ImageName has been updated with question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iUpdatedPreviousImageNameQuestions(String image3, String solution3) throws Throwable {
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
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[2].image", is(image3)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[2].solution", is(solution3)));
    }

    @When("^I edit the previous ImageName deleting question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iEditPreviousImageNameAndDeleteQuestion(String image1, String solution1) throws Throwable {
        String questionUri;

        if (getImageNameGameStepDefs.imageNameWriteQuestion1.getImage().equals(image1) && getImageNameGameStepDefs.imageNameWriteQuestion1.getSolution().equals(solution1)) {
            questionUri = getImageNameGameStepDefs.imageNameWriteQuestion1.getUri();
        } else {
            questionUri = getImageNameGameStepDefs.imageNameWriteQuestion2.getUri();
        }

        stepDefs.result = stepDefs.mockMvc.perform(
                delete(questionUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageName has been updated without question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iUpdatedPreviousImageNameQuestionDeleted(String image, String solution) throws Throwable {
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
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions", hasSize(1)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[0].image", not(image)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[0].solution", not(solution)));
    }

    @When("^I edit the previous ImageName deleting QuestionsList")
    public void iEditPreviousImageNameAndDeleteQuestionList() throws Throwable {

        String questionUri = getImageNameGameStepDefs.imageNameWriteQuestion1.getUri();
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(questionUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        questionUri = getImageNameGameStepDefs.imageNameWriteQuestion2.getUri();
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(questionUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageName has been updated without questionList")
    public void iUpdatedPreviousImageNameQuestionListDeleted() throws Throwable {
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
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions", hasSize(0)));
    }

}
