package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.repository.ImageNameQuestionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EditImageNameQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameQuestionStepDefs getImageNameQuestionStepDefs;

    @Autowired
    private ImageNameQuestionRepository imageNameQuestionRepository;

    @When("^I edit the previous ImageNameQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iEditPreviousImageNameQuestion(String image, String solution) throws Throwable{
        JSONObject imageNameQuestion = new JSONObject();
        imageNameQuestion.put("image", image);
        imageNameQuestion.put("solution", solution);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameQuestionStepDefs.newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imageNameQuestion.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageNameQuestion has been updated with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iCheckThatImageNameQuestionHasBeenUpdated(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image", is(image)))
                .andExpect(jsonPath("$.solution", is(solution)));
    }
}
