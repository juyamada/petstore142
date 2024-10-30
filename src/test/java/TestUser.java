import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

public class TestUser{
    String ct = "application/json";
    String uriUser = "https://petstore.swagger.io/v2/user";

@Test 
    public void testLogin(){
        //Configura
        String username = "arya";
        String password = "123456";

        String resultadoEsperado = "logged in user session:";

        Response resposta = (Response) given()  
        .contentType(ct)
        .log().all()

        //Executa
        .when()
            .get(uriUser + "/login?username=" + username + "&password=" + password)

        //Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("code",is (200))
            .body("type", is ("unknown"))
            .body("message", containsString(resultadoEsperado))  //contem
            .body("message", hasLength(36))  //
        .extract()
        ;
        // extração
        String token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("Conteúdo do Token:" + token);

    }

}
