package cat.udl.eps.softarch.geolearning.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import net.minidev.json.JSONObject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EditGameStepDefs {

	@Autowired
	private StepDefs stepDefs;
	
	@When("I edit the ImageName and set the instruccions at {string}")
	public void i_edit_the_ImageName_and_set_the_instruccions_at(String instruccions) throws Throwable {
		
		JSONObject data = new JSONObject();
		data.put("instructions", instruccions);
		
		stepDefs.result = stepDefs.mockMvc.perform(
				patch(stepDefs.newResourceUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(data.toString())
				.accept(MediaType.APPLICATION_JSON)
				.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
	}

	@Then("The ImageName has the instructions at {string}")
	public void the_ImageName_has_the_instructions_at(String instruccions) throws Throwable {
	    
        stepDefs.result = stepDefs.mockMvc.perform(
                get(stepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instruccions)));
	}
	
	@When("I edit the ImageImage and set the instructions at {string}")
	public void i_edit_the_ImageImage_and_set_the_instructions_at(String instruccions) throws Throwable {
		JSONObject data = new JSONObject();
		data.put("instructions", instruccions);
		
		stepDefs.result = stepDefs.mockMvc.perform(
				patch(stepDefs.newResourceUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(data.toString())
				.accept(MediaType.APPLICATION_JSON)
				.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
	}

	@Then("The ImageImage has the instructions at {string}")
	public void the_ImageImage_has_the_instructions_at(String instruccions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(stepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instruccions)));
	}

	@When("I edit the ImageOption and set the instructions at {string}")
	public void i_edit_the_ImageOption_and_set_the_instructions_at(String instruccions) throws Throwable {
		JSONObject data = new JSONObject();
		data.put("instructions", instruccions);
		
		stepDefs.result = stepDefs.mockMvc.perform(
				patch(stepDefs.newResourceUri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(data.toString())
				.accept(MediaType.APPLICATION_JSON)
				.with(AuthenticationStepDefs.authenticate()))
				.andDo(print());
	}

	@Then("The ImageOption has the instructions at {string}")
	public void the_ImageOption_has_the_instructions_at(String instruccions) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                get(stepDefs.newResourceUri)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.instructions", is(instruccions)));
	}

}
