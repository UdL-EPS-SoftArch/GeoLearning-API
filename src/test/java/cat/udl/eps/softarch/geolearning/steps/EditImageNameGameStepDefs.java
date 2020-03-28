package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameQuestionRepository;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonObject;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EditImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameGameStepDefs getImageNameGameStepDefs;

    @Autowired
    private ImageNameQuestionRepository imageNameQuestionRepository;

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

        ImageNameQuestion imageNameQuestion1 = new ImageNameQuestion();
        imageNameQuestion1.setImageName(getImageNameGameStepDefs.imageName);
        imageNameQuestion1.setImage(image1);
        imageNameQuestion1.setSolution(solution1);
        String question1 = imageNameQuestionRepository.save(imageNameQuestion1).getUri();

        ImageNameQuestion imageNameQuestion2 = new ImageNameQuestion();
        imageNameQuestion2.setImageName(getImageNameGameStepDefs.imageName);
        imageNameQuestion2.setImage(image2);
        imageNameQuestion2.setSolution(solution2);
        String question2 = imageNameQuestionRepository.save(imageNameQuestion2).getUri();

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

        ImageNameQuestion imageNameQuestion1 = new ImageNameQuestion();
        imageNameQuestion1.setImageName(getImageNameGameStepDefs.imageName);
        imageNameQuestion1.setImage(image);
        imageNameQuestion1.setSolution(solution);
        String question = imageNameQuestionRepository.save(imageNameQuestion1).getUri();

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
                .andExpect(jsonPath("$._embedded.imageNameQuestions[0].image", is(image1)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[0].solution", is(solution1)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[1].image", is(image2)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[1].solution", is(solution2)));
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
                .andExpect(jsonPath("$._embedded.imageNameQuestions[2].image", is(image3)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[2].solution", is(solution3)));
    }

    @When("^I edit the previous ImageName deleting question with \"([^\"]*)\" and \"([^\"]*)\"")
    public void iEditPreviousImageNameAndDeleteQuestion(String image1, String solution1) throws Throwable {
        String questionUri;

        if (getImageNameGameStepDefs.imageNameQuestion1.getImage().equals(image1) && getImageNameGameStepDefs.imageNameQuestion1.getSolution().equals(solution1)) {
            questionUri = getImageNameGameStepDefs.imageNameQuestion1.getUri();
        } else {
            questionUri = getImageNameGameStepDefs.imageNameQuestion2.getUri();
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
                .andExpect(jsonPath("$._embedded.imageNameQuestions", hasSize(1)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[0].image", not(image)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[0].solution", not(solution)));
    }

    @When("^I edit the previous ImageName deleting QuestionsList")
    public void iEditPreviousImageNameAndDeleteQuestionList() throws Throwable {

        String questionUri = getImageNameGameStepDefs.imageNameQuestion1.getUri();
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(questionUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        questionUri = getImageNameGameStepDefs.imageNameQuestion2.getUri();
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
                .andExpect(jsonPath("$._embedded.imageNameQuestions", hasSize(0)));
    }

}
