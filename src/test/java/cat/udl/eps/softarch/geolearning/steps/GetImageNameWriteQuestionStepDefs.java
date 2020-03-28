package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetImageNameWriteQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ImageNameWriteQuestionRepository writeQuestionRepository;
    private ImageNameWriteQuestion imageNameWriteQuestion;

    protected String newResourceUri;


    @And("^There is an ImageNameWriteQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void thereIsAnImageNameWriteQuestion(String image, String solution) throws Throwable {
        imageNameWriteQuestion = new ImageNameWriteQuestion();
        imageNameWriteQuestion.setImage(image);
        imageNameWriteQuestion.setSolution(solution);
        newResourceUri = writeQuestionRepository.save(imageNameWriteQuestion).getUri();
    }

    @When("^I request the previous ImageNameWriteQuestion")
    public void iRequestTheImageNameWriteQuestion() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I request the ImageNameWriteQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iRequestTheImageNameWriteQuestion(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image",is(image)))
                .andExpect(jsonPath("$.solution",is(solution)));
    }
}
