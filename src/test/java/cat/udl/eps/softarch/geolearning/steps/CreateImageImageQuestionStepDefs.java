package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.*;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateImageImageQuestionStepDefs {
    @Autowired
    private StepDefs stepDefs;

    private String newResourceUri;

    public String getNewResourceUri() {
        return newResourceUri;
    }


    @When("I create a new ImageImageQuestion with image {string} and solution {string}")
    public void iCreateANewImageImageQuestionWithImageAndSolution(String arg0, String arg1) throws Throwable {
        ImageImageQuestion iiQ = new ImageImageQuestion();
        iiQ.setImage(arg0);
        iiQ.setSolution(arg1);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageImageQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(iiQ))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }


    @And("It has not been created a ImageImageQuestion with image {string} and solution {string}")
    public void itHasNotBeenCreatedAImageImageQuestionWithImageAndSolution(String arg0, String arg1) throws Throwable {
        Assert.assertNull(newResourceUri);
    }

    @And("It has been created a ImageImageQuestion with image {string} and solution {string}")
    public void itHasBeenCreatedAImageImageQuestionWithImageAndSolution(String arg0, String arg1) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image", is(arg0)))
                .andExpect(jsonPath("$.solution", is(arg1)));
    }
}
