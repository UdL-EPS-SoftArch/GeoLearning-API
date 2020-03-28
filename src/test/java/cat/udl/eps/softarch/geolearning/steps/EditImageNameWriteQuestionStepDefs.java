package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EditImageNameWriteQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameWriteQuestionStepDefs getImageNameWriteQuestionStepDefs;

    @Autowired
    private ImageNameWriteQuestionRepository imageNameWriteQuestionRepository;

    @When("^I edit the previous ImageNameWriteQuestion with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iEditPreviousImageNameWriteQuestion(String image, String solution) throws Throwable{
        JSONObject imageNameWriteQuestion = new JSONObject();
        imageNameWriteQuestion.put("image", image);
        imageNameWriteQuestion.put("solution", solution);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameWriteQuestionStepDefs.newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imageNameWriteQuestion.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageNameWriteQuestion has been updated with image \"([^\"]*)\" and solution \"([^\"]*)\"")
    public void iCheckThatImageNameWriteQuestionHasBeenUpdated(String image, String solution) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameWriteQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image", is(image)))
                .andExpect(jsonPath("$.solution", is(solution)));
    }
}
