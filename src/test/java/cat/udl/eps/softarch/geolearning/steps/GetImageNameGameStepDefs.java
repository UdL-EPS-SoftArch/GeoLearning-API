package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageName;
import cat.udl.eps.softarch.geolearning.domain.ImageNameQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameRepository;
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

    protected ImageName imageName;
    protected ImageNameQuestion imageNameQuestion1;
    protected ImageNameQuestion imageNameQuestion2;
    protected String newResourceUri;

    @And("^There is an ImageName with instructions \\\"([^\\\"]*)\\\"")
    public void thereIsAnImageNameWithInstructions (String instructions) throws Throwable {
        imageName = new ImageName();
        imageName.setInstructions(instructions);
        newResourceUri = imageNameRepository.save(imageName).getUri();
    }

    @And("^There is an ImageName with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void thereIsAnImageNameWithInstructionsAndQuestions (String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        imageName = new ImageName();
        imageName.setInstructions(instructions);

        imageNameQuestion1 = new ImageNameQuestion();
        imageNameQuestion1.setImage(image1);
        imageNameQuestion1.setSolution(response1);
        imageNameQuestion1.setImageName(imageName);

        imageNameQuestion2 = new ImageNameQuestion();
        imageNameQuestion2.setImage(image2);
        imageNameQuestion2.setSolution(response2);
        imageNameQuestion2.setImageName(imageName);

        List<ImageNameQuestion> questions = new ArrayList<>();
        questions.add(imageNameQuestion1);
        questions.add(imageNameQuestion2);

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
                .andExpect(jsonPath("$._embedded.imageNameQuestions[0].solution", is(response1)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[1].image", is(image2)))
                .andExpect(jsonPath("$._embedded.imageNameQuestions[1].solution", is(response2)));



    }

}
