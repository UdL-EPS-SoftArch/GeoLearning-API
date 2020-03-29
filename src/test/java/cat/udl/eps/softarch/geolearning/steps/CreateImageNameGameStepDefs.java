package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageName;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CreateImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @When("^I create a new ImageName with instructions \"([^\"]*)\"")
    public void iCreateANewImageNameWithInstructions(String instructions) throws Throwable {
        stepDefs.imageName = new ImageName();
        stepDefs.imageName.setInstructions(instructions);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(stepDefs.imageName))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        stepDefs.newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a ImageName with instructions \\\"([^\\\"]*)\\\"")
    public void itHasBeenCreatedAImageNameWithInstructions(String instructions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(stepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));
    }


    @And("^It has not been created an ImageName")
    public void itHasNotBeenCreatedAnImageName() throws Throwable {
        assert stepDefs.newResourceUri == null;
    }

    @When("^I create a new ImageName without instructions")
    public void iCreateANewImageNameWithoutInstructions() throws Throwable {
        stepDefs.imageName = new ImageName();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(stepDefs.imageName))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        stepDefs.newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }
}
