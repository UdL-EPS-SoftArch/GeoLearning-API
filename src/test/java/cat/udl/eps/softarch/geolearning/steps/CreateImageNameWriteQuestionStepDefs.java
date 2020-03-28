package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
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

public class CreateImageNameWriteQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;
    private ImageNameWriteQuestion writeQuestion;
    private String newResourceUri;

    @When("^I create a new ImageNameWriteQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iCreateANewImageNameWriteQuestionWithImageAndSolution(String image, String solution) throws Throwable {
        writeQuestion = new ImageNameWriteQuestion();
        writeQuestion.setImage(image);
        writeQuestion.setSolution(solution);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNameWriteQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(writeQuestion))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has not been created a ImageNameWriteQuestion")
    public void itHasBeenCreatedAnImageNameWriteQuestionWithImageAndSolution() throws Throwable {
        assert newResourceUri == null;
    }

    @And("^It has been created a ImageNameWriteQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void itHasBeenCreatedAnImageNameWriteQuestionWithImageAndSolution(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image",is(image)))
                .andExpect(jsonPath("$.solution",is(solution)));
    }
}
