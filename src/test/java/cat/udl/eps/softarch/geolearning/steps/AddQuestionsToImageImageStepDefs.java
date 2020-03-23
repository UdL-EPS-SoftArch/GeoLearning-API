package cat.udl.eps.softarch.geolearning.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import io.cucumber.java.en.When;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddQuestionsToImageImageStepDefs {

    String newResourceUriQ;

    String newResourceUriI;

    String newResourceUriPatch;


    @Autowired
    CreateGameStepDefs cg;

    @Autowired
    CreateImageImageQuestionStepDefs ciiq;

    @Autowired
    private StepDefs stepDefs;


    @Given("There is a created ImageImage with instructions {string}")
    public void thereIsACreatedImageImageWithInstructions(String arg0) throws Throwable {
        cg.iCreateANewImageImageWithInstructions(arg0);
        newResourceUriI = cg.getNewResourceUri();
    }

    @And("There is a ImageImageQuestion with image {string} and solution {string}")
    public void thereIsAImageImageQuestionWithImageAndSolution(String arg0, String arg1) throws Throwable {
       ciiq.iCreateANewImageImageQuestionWithImageAndSolution(arg0, arg1);
       newResourceUriQ = ciiq.getNewResourceUri();
    }

    @When("Content Creator add ImageImageQuestion with image {string} and solution {string} to a ImageImage with instructions {string}")
    public void contentCreatorAddImageImageQuestionWithImageAndSolutionToAImageImageWithInstructions(String arg0, String arg1, String arg2) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                post(newResourceUriI+"/questions")
                        .contentType("text/uri-list")
                        .content(newResourceUriQ)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        String split[] = newResourceUriQ.split("/");
        newResourceUriPatch = split[1];
    }

    @And("There is a ImageImageQuestion with image {string} and solution {string} in a ImageImage with instructions {string}")
    public void thereIsAImageImageQuestionWithImageAndSolutionInAImageImagewithInstructions(String arg0, String arg1, String arg2) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUriI+"/questions/{id}", newResourceUriPatch)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }
}
