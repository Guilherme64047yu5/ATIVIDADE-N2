package br.com.bookstore;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiRestAssuredTest {
 @LocalServerPort int port; static String cookie;
 @BeforeEach void setup(){RestAssured.port=port;}
 @BeforeAll static void init(){}
 private String login(){Response r=given().contentType("application/json").body(Map.of("email","admin@bookstore.com","senha","123456")).post("/api/auth/login");return r.getCookie("JSESSIONID");}
 @Test void deveAutenticarUsuario(){given().contentType("application/json").body(Map.of("email","admin@bookstore.com","senha","123456")).post("/api/auth/login").then().statusCode(200).body("mensagem",equalTo("Login realizado com sucesso."));}
 @Test void deveListarLivrosAposLogin(){String c=login();given().cookie("JSESSIONID",c).get("/api/livros").then().statusCode(200).body("$",notNullValue());}
}
