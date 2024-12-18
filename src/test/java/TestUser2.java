// 0 - Pacotes



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.google.gson.Gson;

// 1 - Bibliotecas

// 2 - Classes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser2 {
    // 2.1 - Atributos
    static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user";
    
    //int code = 200; //Saída
    //String type = "unknown"; //Saída
    //String message = "150"; //Saída
    String username = "lia10";
    String firstName = "Lia";
    String lastName = "Fonseca";

    // 2.2 - Métodos e Funções

    public static String lerArquivoJson(String arquivoJson) throws IOException {
        return new String (Files.readAllBytes(Paths.get(arquivoJson)));

    }

    @Test @Order(1)
    public void testPostUser() throws IOException {
            // Configura
    
            String jsonBody = lerArquivoJson("src/test/resources/json/user1.json"); // Entrada
            String id = "10";
            // começa o teste via REST -assured
            
            given()
                    .contentType(ct)
                    .log().all()
                    .body(jsonBody)
            
            // Executa
            .when()
                    .post(uriUser)
            
                    // Valida
            .then()
                    .log().all()
                    .statusCode(200)
                    .body("code", is (200))
                    .body("message", is (id))
                ;
    }
    
    @Test @Order(2)
    public void testGetUser() throws IOException {
                //Configura
                String jsonBody = lerArquivoJson("src/test/resources/json/user1.json"); // Entrada
                int id = 10;

                given()
                    .contentType(ct)
                    .log().all()
                    //.body(jsonBody)
        
                //Executa
                .when()
                    .get(uriUser + "/" + username)
        
                //Valida
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", is (id))
                    .body("firstName", is (firstName))
                    .body("lastName", is (lastName))
        ;            
    }

        @Test @Order(3)
        public void testPutUser() throws IOException {
        //Configura
        String jsonBody = lerArquivoJson("src/test/resources/json/user2.json");
        
        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)

        //Executa
        .when()
            .put(uriUser + "/" + username)

        //Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is ("unknown"))
            ;
    }

    @Test @Order(4)
    public void testDeleteUser() throws IOException {
    //Configura
    String jsonBody = lerArquivoJson("src/test/resources/json/user2.json");
    
    given()
        .contentType(ct)
        .log().all()
        .body(jsonBody)

    //Executa
    .when()
        .delete(uriUser + "/" + username)
        
    //Valida
    .then()
        .log().all()
        .statusCode(200)
        .body("code", is (200))
        .body("type", is ("unknown"))
        .body("message", is (username))

    ;

    }

    @ParameterizedTest @Order(5)
    @CsvFileSource( resources = "/csv/userMassa.csv", numLinesToSkip = 1, delimiter = ',')
    public void testPostUserDDT(
        //estrutura de dados do arquivo csv
        String id,  //coloquei como string pois no response body, ele retorna como string
        String username,
        String firstName,
        String lastName,
        String email,
        int userStatus

    ){// inicio do código do método testPostUserDDT
      
        //Criar a classe user para receber os dados do csv

      User2 user2 = new User2();
      
      user2.id = id;
      user2.username = username;
      user2.firstName = firstName;
      user2.lastName = lastName;
      user2.email = email;
      user2.userStatus = userStatus;

      // Criar um json para o body a ser enviado a partir da classe User2 e do CSV
        Gson gson = new Gson();  // Instancia a classe Gson como o objeto gson
        String jsonBody = gson.toJson(user2);
        
    given()
        .contentType(ct)
        .log().all()
        .body(jsonBody)
    .when()
        .post(uriUser)
    .then()
        .log().all()
        .statusCode(200)
        .body("message", is(id))
        ;
    }

    }


    
