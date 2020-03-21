package cat.udl.eps.softarch.geolearning.steps;


import cat.udl.eps.softarch.geolearning.domain.ImageOptionQuestion;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateImageOptionQuestionStepDefs {

    @Autowired
    private StepDefs stepDefs;


    public String newResourceUri;


    public String getNewResourceUri(){
        return newResourceUri;
    }

    @When("I create a new ImageOptionQuestion with image {string}  solution {string} and  optionA {string}, optionB {string}, optionC {string}, optionD {string}, optionE {string}")
    public void i_create_a_new_ImageOptionQuestion_with_image_solution_and_optionA_optionB_optionC_optionD_optionE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        ImageOptionQuestion iiQ = new ImageOptionQuestion();
        iiQ.setImage(arg0);
        iiQ.setSolution(arg1);
        iiQ.setOptionA(arg2);
        iiQ.setOptionB(arg3);
        iiQ.setOptionC(arg4);
        iiQ.setOptionD(arg5);
        iiQ.setOptionE(arg6);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageOptionQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(iiQ))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("It has not been created a ImageOptionQuestion with image {string}  solution {string} and  optionA {string}, optionB {string}, optionC {string}, optionD {string}, optionE {string}")
    public void itHasNotBeenCreatedAImageOptionQuestionWithImageSolutionAndOptionAOptionBOptionCOptionDOptionE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        Assert.assertNull(newResourceUri);
    }

    @And("It has been created a ImageOptionQuestion with image {string}  solution {string} and  optionA {string}, optionB {string}, optionC {string}, optionD {string}, optionE {string}")
    public void itHasBeenCreatedAImageOptionQuestionWithImageSolutionAndOptionAOptionBOptionCOptionDOptionE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.image", is(arg0)))
                .andExpect(jsonPath("$.solution", is(arg1)))
                .andExpect(jsonPath("$.optionA", is(arg2)))
                .andExpect(jsonPath("$.optionB", is(arg3)))
                .andExpect(jsonPath("$.optionC", is(arg4)))
                .andExpect(jsonPath("$.optionD", is(arg5)))
                .andExpect(jsonPath("$.optionE", is(arg6)));
    }
}
