package cat.udl.eps.softarch.geolearning.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteImageNameWriteQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameWriteQuestionStepDefs getImageNameWriteQuestionStepDefs;

    @When("^I delete the previous ImageNameWriteQuestion")
    public void iDeleteThePreviousImageNameWriteQuestion() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(getImageNameWriteQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^Previous ImageNameWriteQuestion has been deleted")
    public void previousImageNameHasBeenDeleted() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameWriteQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
