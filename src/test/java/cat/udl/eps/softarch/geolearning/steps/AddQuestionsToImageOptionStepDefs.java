package cat.udl.eps.softarch.geolearning.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import io.cucumber.java.en.When;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddQuestionsToImageOptionStepDefs {

    String newResourceUriQ;

    String newResourceUriI;

    String newResourceUriPatch;


    @Autowired
    CreateGameStepDefs cg;

    @Autowired
    CreateImageOptionQuestionStepDefs cioq;

    @Autowired
    private StepDefs stepDefs;


    @Given("There is a created ImageOption with instructions {string}")
    public void there_is_a_created_ImageOption_with_instructions(String arg0) throws Throwable {
        cg.iCreateANewImageOptionWithInstructions(arg0);
        newResourceUriI = cg.getNewResourceUri();
    }

    @And("There is a ImageOptionQuestion with image {string}, solution {string} and  optionA {string}, optionB {string}, optionC {string}, optionD {string}, optionE {string}")
    public void thereIsAImageOptionQuestionWithImageSolutionAndOptionAOptionBOptionCOptionDOptionE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        cioq.i_create_a_new_ImageOptionQuestion_with_image_solution_and_optionA_optionB_optionC_optionD_optionE(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        newResourceUriQ = cioq.getNewResourceUri();
    }

    @When("Content Creator add ImageOptionQuestion with image {string}, solution {string} and  optionA {string}, optionB {string}, optionC {string}, optionD {string}, optionE {string} to a ImageOption with instructions {string}")
    public void contentCreatorAddImageOptionQuestionWithImageSolutionAndOptionAOptionBOptionCOptionDOptionEToAImageOptionWithInstructions(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws Throwable{
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


}
