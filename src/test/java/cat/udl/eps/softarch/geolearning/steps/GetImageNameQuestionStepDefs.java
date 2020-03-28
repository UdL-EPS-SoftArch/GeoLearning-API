package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameQuestionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetImageNameQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private ImageNameQuestionRepository writeQuestionRepository;
    private ImageNameQuestion imageNameQuestion;

    protected String newResourceUri;


    @And("^There is an ImageNameQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void thereIsAnImageNameQuestion(String image, String solution) throws Throwable {
        imageNameQuestion = new ImageNameQuestion();
        imageNameQuestion.setImage(image);
        imageNameQuestion.setSolution(solution);
        newResourceUri = writeQuestionRepository.save(imageNameQuestion).getUri();
    }

    @When("^I request the previous ImageNameQuestion")
    public void iRequestTheImageNameQuestion() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I request the ImageNameQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iRequestTheImageNameQuestion(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image",is(image)))
                .andExpect(jsonPath("$.solution",is(solution)));
    }
}
