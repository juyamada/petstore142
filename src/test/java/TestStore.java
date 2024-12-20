// 1- Bibliotecas

// 2- Classes

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

        // Executa
        .when()
            .get(uriStore + '/' + id)

        // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is(id))
            .body("petId", is(petId))
            .body("quantity", is (quantity))
            .body("complete", is (true))
            ;   
    }
    @Test @Order(3)
    public void testDeleteStore(){
        // Configura
        String id = "5";
        given()
            .contentType(ct)
            .log().all()
        // Executa
        .when()
            .delete(uriStore + '/' + id)
        
        // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is (200))
            .body("type", is ("unknown"))
            .body("message", is (String.valueOf(id)))
            ;

    }
    @ParameterizedTest @Order(4)
    @CsvFileSource( resources = "/csv/storeMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testPostStoreDDT(
        //estrutura de dados do arquivo csv
        int id,
        int petId,
        int quantity,
        String shipDate,
        String status,
        boolean complete
        

    ){// inicio do código do método testPostUserDDT
      
        //Criar a classe user para receber os dados do csv

      Store store = new Store();
      
      store.id = id;
      store.petId = petId;
      store.quantity = quantity;
      store.shipDate = shipDate;
      store.status = status;
      store.complete = complete;

      // Criar um json para o body a ser enviado a partir da classe Store e do CSV
        Gson gson = new Gson();  // Instancia a classe Gson como o objeto gson
        String jsonBody = gson.toJson(store);
        
    given()
        .contentType(ct)
        .log().all()
        .body(jsonBody)
    .when()
        .post(uriStore)
    .then()
        .log().all()
        .statusCode(200)
        .body("id", is(id))
        .body("petId", is (petId))
        .body("status", is (status))
        .body("complete", is (true))
        ;
    }

}
