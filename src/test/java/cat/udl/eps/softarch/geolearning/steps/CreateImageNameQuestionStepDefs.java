package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameQuestion;
import cat.udl.eps.softarch.geolearning.repository.GameRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateImageNameQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;
    private ImageNameQuestion writeQuestion;
    private String newResourceUri;

    @When("^I create a new ImageNameQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iCreateANewImageNameQuestionWithImageAndSolution(String image, String solution) throws Throwable {
        writeQuestion = new ImageNameQuestion();
        writeQuestion.setImage(image);
        writeQuestion.setSolution(solution);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNameQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(writeQuestion))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has not been created a ImageNameQuestion")
    public void itHasBeenCreatedAnImageNameQuestionWithImageAndSolution() throws Throwable {
        assert newResourceUri == null;
    }

    @And("^It has been created a ImageNameQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void itHasBeenCreatedAnImageNameQuestionWithImageAndSolution(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image",is(image)))
                .andExpect(jsonPath("$.solution",is(solution)));
    }
}
