package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.repository.GameRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EditImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GetImageNameGameStepDefs getImageNameGameStepDefs;
    private GameRepository gameRepository;
    private String newResourceUri;

    @When("^I edit the previous ImageName with instructions \"([^\"]*)\"")
    public void iEditThePreviousImageName(String instructions) throws Throwable {

        JSONObject imageName = new JSONObject();
        imageName.put("instructions",instructions);

        stepDefs.result = stepDefs.mockMvc.perform(
                patch(getImageNameGameStepDefs.newResourceUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imageName.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("^ImageName has been updated with intructions \"([^\"]*)\"")
    public void iCheckThatImageNameHasBeenUpdated(String instructions) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                get(getImageNameGameStepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));
    }



}
