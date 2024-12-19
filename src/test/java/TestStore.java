// 1- Bibliotecas

// 2- Classes

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class TestStore {
    
    // 2.1 - atributos
    static String ct = "application/json";
    static String uriStore = "https://petstore.swagger.io/v2/store/order";
    int id = 5;
    int petId = 485;
    int quantity = 1;
    String status = "placed";
    boolean complete = true;

    // Função de leitura de json
    public static String lerArquivoJson(String arquivoJson) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
        
    }
    
    @Test @Order(1)
    public void testPostStore() throws IOException{
        // Configura
        String jsonBody = lerArquivoJson("src/test/resources/json/store1.json");
        
        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)

        // Executa
        .when()
            .post(uriStore)

         // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(id))
            .body("petId", is (petId))
            .body("status", is (status))
            .body("complete", is (true))
            ;
    }
    @Test @Order(2)
    public void testGetStore(){
        // Configura
        given()
            .contentType(ct)
            .log().all()
            //.body(jsonBody)

        .when()
            .get(uriStore + '/' + id)
        // Executa
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(id))
            .body("petId", is(petId))
            .body("quantity", is (quantity))
            .body("complete", is (true))
            ;
        // Valida
    }

}
