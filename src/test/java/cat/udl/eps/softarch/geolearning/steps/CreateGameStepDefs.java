package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.*;
import cat.udl.eps.softarch.geolearning.repository.ImageImageRepository;
import cat.udl.eps.softarch.geolearning.repository.ImageOptionRepository;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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


	  private GameRepository gameRepository;

	  private ImageName imageName1;


	  private ImageImage imageImage1;


	  private ImageOption imageOption1;


	  private ImageImageRepository iiR;

	  private ImageOptionRepository ioR;


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

	@When("^I create a new ImageImage with instructions \"([^\"]*)\"")
	public void iCreateANewImageImageWithInstructions(String instructions) throws Throwable {
		imageImage1 = new ImageImage();
		imageImage1.setInstructions(instructions);
		stepDefs.result = stepDefs.mockMvc.perform(
				post("/imageImages")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								stepDefs.mapper.writeValueAsString(imageImage1))
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
		newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
	}

	@And("^It has been created a ImageImage with instructions \\\"([^\\\"]*)\\\"")
	public void itHasBeenCreatedAImageImageWithInstructions(String instructions) throws Throwable {
		stepDefs.result = stepDefs.mockMvc.perform(
				get(newResourceUri)
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print())
				.andExpect(jsonPath("$.instructions", is(instructions)));
	}

	@When("^I create a new ImageOption with instructions \"([^\"]*)\"")
	public void iCreateANewImageOptionWithInstructions(String instructions) throws Throwable {
		imageOption1 = new ImageOption();
		imageOption1.setInstructions(instructions);
		stepDefs.result = stepDefs.mockMvc.perform(
				post("/imageOptions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								stepDefs.mapper.writeValueAsString(imageOption1))
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
		newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
	}

	@And("^It has been created a ImageOption with instructions \\\"([^\\\"]*)\\\"")
	public void itHasBeenCreatedAImageOptionWithInstructions(String instructions) throws Throwable {
		stepDefs.result = stepDefs.mockMvc.perform(
				get(newResourceUri)
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print())
				.andExpect(jsonPath("$.instructions", is(instructions)));
	}

	@And("It has not been created a ImageImage with instructions {string}")
	public void itHasNotBeenCreatedAImageImageWithInstructions(String arg0) {
		Assert.assertNull(newResourceUri);

	}

	@And("It has not been created a ImageOption with instructions {string}")
	public void itHasNotBeenCreatedAImageOptionWithInstructions(String arg0) {
		Assert.assertNull(newResourceUri);

	}
}
