package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageName;
import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonArray;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonObject;
import io.cucumber.core.internal.gherkin.deps.com.google.gson.JsonParser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ImageNameRepository imageNameRepository;
    private ImageNameWriteQuestionRepository imageNameWriteQuestionRepository;
    private ImageNameWriteQuestion imageNameWriteQuestion1;
    private ImageNameWriteQuestion imageNameWriteQuestion2;

    private ImageName imageName;
    private int id;
    private String newResourceUri;

    @And("^There is an ImageName with instructions \\\"([^\\\"]*)\\\"")
    public void thereIsAnImageNameWithInstructions (String instructions) throws Throwable {
        imageName = new ImageName();
        imageName.setId(id);
        imageName.setInstructions(instructions);
        newResourceUri = imageNameRepository.save(imageName).getUri();
    }

    @And("^There is an ImageName with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void thereIsAnImageNameWithInstructionsAndQuestions (String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        imageName = new ImageName();
        imageName.setInstructions(instructions);

        imageNameWriteQuestion1 = new ImageNameWriteQuestion();
        imageNameWriteQuestion1.setImage(image1);
        imageNameWriteQuestion1.setSolution(response1);
        imageNameWriteQuestion1.setImageName(imageName);

        imageNameWriteQuestion2 = new ImageNameWriteQuestion();
        imageNameWriteQuestion2.setImage(image2);
        imageNameWriteQuestion2.setSolution(response2);
        imageNameWriteQuestion2.setImageName(imageName);

        List<ImageNameWriteQuestion> questions = new ArrayList<>();
        questions.add(imageNameWriteQuestion1);
        questions.add(imageNameWriteQuestion2);

        imageName.setQuestions(questions);

        newResourceUri = imageNameRepository.save(imageName).getUri();

    }

    @When("^I request the previous ImageName")
    public void iRequestThePreviousImageName () throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I request the ImageName with instructions \\\"([^\\\"]*)\\\"")
    public void iRequestTheImageNameWithInstructions (String instructions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));
    }

    @When("^I request the ImageName with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void iRequestTheImageNameWithInstructionsAndQuestions (String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = new JsonObject();
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
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[0].solution", is(response1)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[1].image", is(image2)))
                .andExpect(jsonPath("$._embedded.imageNameWriteQuestions[1].solution", is(response2)));



    }

}
