package api;

import api.ApiBaseFlow;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.Test;



public class ApiAutomationTestFlow extends ApiBaseFlow {


    /**
     * this test will pass the value of capital which extracted from Countries API and send get request
     * and will assert that the response code is 200 (Success)
     * will also verify the response schema
     * the last thing the test will verify that the currency from the countries API is the same as from capital API
     */

    @Test
    public void validTest() {
       //extract the capital name and the currency fron countries api
        capital=extractSpecificValue("fields","name;capital;currencies;latlng",countriesURL,"capital",0);
        currency=extractSpecificValue("fields","name;capital;currencies;latlng",countriesURL,"currencies[0].code",0);
        System.out.println("the extracted capital name from countries API is : " + capital+ " and the currency is :"+currency);

        //send the request
        response = getResponse("fields", "name;capital;currencies;latlng;regionalBlocs", capital,capitalURI);
        //print the response
        response.prettyPrint();
        // verify the Status code and schema
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ValidTestSchema.json"));


        capitalCurrency = response.then().extract().jsonPath().getList("currencies[0].code").get(0).toString();
        System.out.println("THE CURRENCY FROM CAPITAL API IS :"+capitalCurrency);
       Assert.assertEquals(capitalCurrency, currency, "Both capitals not Equal");


    }


    /**
     * this negative test will pass wrong value of the capital name and expect to get 404(not found ) error
     */
    @Test
    public void inValidTest() {

        //send the request with wrong Capital name to see if the response will return not found error
        response = getResponse("fields", "name;capital;currencies;latlng;regionalBlocs", "WrongValue",capitalURI);
        //print the response
        response.prettyPrint();
        // verify the Status code(404) not found and schema
        response
                .then()
                .assertThat()
                .statusCode(404)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("InvalidTestSchema.json"));

    }


}
