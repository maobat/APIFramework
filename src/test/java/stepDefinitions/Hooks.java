package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace") 
	public void beforeScenario() throws IOException {
		//write a code that will give you place id
		//execute this code only when place id is null
		
		StepDefinitions m=new StepDefinitions();
		
		if(StepDefinitions.place_id==null) {
			m.add_place_payload_with("Maobat", "Italian", "Europe");
			m.users_calls_with_http_request("addPlaceAPI","POST");
			m.verify_place_id_created_maps_to_using("Maobat", "getPlaceAPI");
		}
	}
}
