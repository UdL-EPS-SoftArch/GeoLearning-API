package cat.udl.eps.softarch.geolearning.steps;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CreateGameStepDefs {
	
	  @Autowired
	  private StepDefs stepDefs;

	  @Autowired
	  private GameRepository gameRepository;
		ImageName imageName1;
	private String newResourceUri;


	@When("^I create a new ImageName with instructions \"([^\"]*)\"")
	    public void iCreateANewImageNameWithInstructions(String instructions) throws Throwable {
	        imageName1 = new ImageName();
	        imageName1.setInstructions(instructions);
	        stepDefs.result = stepDefs.mockMvc.perform(
	                post("/imageNames")
							.contentType(MediaType.APPLICATION_JSON)
							.content(
									stepDefs.mapper.writeValueAsString(imageName1))
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
	  
	  
}
