import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import java.net.URI;
import java.net.URISyntaxException;
import static io.restassured.RestAssured.given;

public class ApiBaseFlow {

    public URI countriesURL;
    public URI capitalURI;
    public String capital;
    public String capitalCurrency;
    public Response response;
    public String currency;


    /**
     * @throws URISyntaxException
     */
    @BeforeClass
    public void beforeClass() throws URISyntaxException {
        countriesURL = new URI("https://restcountries.eu/rest/v2/all");
        capitalURI = new URI("https://restcountries.eu/rest/v2/capital/");

    }

    /**
     * to get the response after sending get request with passed parameters and uri
     *
     * @param key
     * @param param
     * @param capitalValue
     * @param uri
     * @return
     */
    public Response getResponse(String key, String param, String capitalValue, URI uri) {
        return given()
                .param(key, param)
                .when()
                .get(uri + capitalValue);

    }


    /**
     * to extract value from the response
     *
     * @param key
     * @param Param
     * @param uri
     * @param extractedPath
     * @param index
     * @return
     */
    public String extractSpecificValue(String key, String Param, URI uri, String extractedPath, int index) {
        return
                given()
                        .param(key, Param)
                        .when()
                        .get(uri)
                        .then()
                        .extract()
                        .jsonPath().getList(extractedPath).get(index).toString();

    }


}
