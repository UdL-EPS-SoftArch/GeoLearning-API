package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.ImageNameWriteQuestion;
import cat.udl.eps.softarch.geolearning.repository.ImageNameWriteQuestionRepository;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import cat.udl.eps.softarch.geolearning.domain.Game;
import cat.udl.eps.softarch.geolearning.domain.ImageName;
import cat.udl.eps.softarch.geolearning.domain.User;
import cat.udl.eps.softarch.geolearning.repository.GameRepository;
import cat.udl.eps.softarch.geolearning.repository.UserRepository;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CreateImageNameGameStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private GameRepository gameRepository;
    ImageNameWriteQuestionRepository ImageNameWriteQuestionRepository;
    ImageName imageName;
    private String newResourceUri;
    ImageNameWriteQuestion imageNameWriteQuestion1;
    ImageNameWriteQuestion imageNameWriteQuestion2;




    @When("^I create a new ImageName with instructions \"([^\"]*)\"")
    public void iCreateANewImageNameWithInstructions(String instructions) throws Throwable {
        imageName = new ImageName();
        imageName.setInstructions(instructions);
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(imageName))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a ImageName with instructions \\\"([^\\\"]*)\\\"")
    public void itHasBeenCreatedAImageNameWithInstructions(String instructions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)));
    }


    @And("^It has not been created an ImageName")
    public void itHasNotBeenCreatedAnImageName() throws Throwable {
        assert newResourceUri == null;
    }

    @When("^I create a new ImageName without instructions")
    public void iCreateANewImageNameWithoutInstructions() throws Throwable {
        imageName = new ImageName();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(imageName))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @When("^I create a new ImageName with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void iCreateANewImageWithInstructionsAndQuestions(String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        imageName = new ImageName();
        imageName.setInstructions(instructions);

        imageNameWriteQuestion1 = new ImageNameWriteQuestion();
        imageNameWriteQuestion1.setImage(image1);
        imageNameWriteQuestion1.setSolution(response1);
        imageNameWriteQuestion1.setImageName(imageName);
        imageNameWriteQuestion2 = new ImageNameWriteQuestion();
        imageNameWriteQuestion2.setImage(image2);
        imageNameWriteQuestion2.setSolution(response2);
        imageNameWriteQuestion2.setImageName(imageName);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/imageNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                stepDefs.mapper.writeValueAsString(imageName))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }

    @And("^It has been created a ImageName with instructions \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\" and question with \\\"([^\\\"]*)\\\" and \\\"([^\\\"]*)\\\"")
    public void itHasBeenCreatedAImageNameWithInstructionsAndQuestions(String instructions, String image1, String response1, String image2, String response2) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instructions)))
                .andExpect(jsonPath("$.questions[0].image", is(image1)))
                .andExpect(jsonPath("$.questions[0].solution", is(response1)))
                .andExpect(jsonPath("$.questions[1].image", is(image2)))
                .andExpect(jsonPath("$.questions[1].solution", is(response2)));
    }
}
