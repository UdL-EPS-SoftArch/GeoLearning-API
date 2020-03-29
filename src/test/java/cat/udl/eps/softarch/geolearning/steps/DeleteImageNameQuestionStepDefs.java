package cat.udl.eps.softarch.geolearning.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteImageNameQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameQuestionStepDefs getImageNameQuestionStepDefs;

    @When("^I delete the previous ImageNameQuestion")
    public void iDeleteThePreviousImageNameQuestion() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(getImageNameQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^Previous ImageNameQuestion has been deleted")
    public void previousImageNameHasBeenDeleted() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameQuestionStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
