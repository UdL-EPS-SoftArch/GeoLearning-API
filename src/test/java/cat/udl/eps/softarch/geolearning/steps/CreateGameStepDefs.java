package cat.udl.eps.softarch.geolearning.steps;

import cat.udl.eps.softarch.geolearning.domain.*;
import cat.udl.eps.softarch.geolearning.repository.ImageImageRepository;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CreateGameStepDefs {

	  @Autowired
	  private StepDefs stepDefs;

	  @Autowired
	  private ImageImageRepository iiR;


	@When("^I create a new ImageImage with instructions \"([^\"]*)\"")
	public void iCreateANewImageImageWithInstructions(String instructions) throws Throwable {
		stepDefs.imageImage1 = new ImageImage();
		stepDefs.imageImage1.setInstructions(instructions);
		stepDefs.result = stepDefs.mockMvc.perform(
				post("/imageImages")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								stepDefs.mapper.writeValueAsString(stepDefs.imageImage1))
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
		stepDefs.newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
	}

	@And("^It has been created a ImageImage with instructions \\\"([^\\\"]*)\\\"")
	public void itHasBeenCreatedAImageImageWithInstructions(String instructions) throws Throwable {
		stepDefs.result = stepDefs.mockMvc.perform(
				get(stepDefs.newResourceUri)
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print())
				.andExpect(jsonPath("$.instructions", is(instructions)));
	}

	@When("^I create a new ImageOption with instructions \"([^\"]*)\"")
	public void iCreateANewImageOptionWithInstructions(String instructions) throws Throwable {
		stepDefs.imageOption1 = new ImageOption();
		stepDefs.imageOption1.setInstructions(instructions);
		stepDefs.result = stepDefs.mockMvc.perform(
				post("/imageOptions")
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								stepDefs.mapper.writeValueAsString(stepDefs.imageOption1))
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
		stepDefs.newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
	}

	@And("^It has been created a ImageOption with instructions \\\"([^\\\"]*)\\\"")
	public void itHasBeenCreatedAImageOptionWithInstructions(String instructions) throws Throwable {
		stepDefs.result = stepDefs.mockMvc.perform(
				get(stepDefs.newResourceUri)
						.accept(MediaType.APPLICATION_JSON)
						.with(AuthenticationStepDefs.authenticate()))
				.andDo(print())
				.andExpect(jsonPath("$.instructions", is(instructions)));
	}

	@And("It has not been created a ImageImage with instructions {string}")
	public void itHasNotBeenCreatedAImageImageWithInstructions(String arg0) {
		Assert.assertNull(stepDefs.newResourceUri);

	}

	@And("It has not been created a ImageOption with instructions {string}")
	public void itHasNotBeenCreatedAImageOptionWithInstructions(String arg0) {
		Assert.assertNull(stepDefs.newResourceUri);

	}
}
