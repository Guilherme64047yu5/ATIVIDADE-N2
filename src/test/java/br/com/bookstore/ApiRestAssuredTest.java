package br.com.bookstore;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiRestAssuredTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    private String login() {
        Response r = given()
                .contentType("application/json")
                .body(Map.of(
                        "email", "admin@bookstore.com",
                        "senha", "123456"
                ))
                .post("/api/auth/login");

        return r.getCookie("JSESSIONID");
    }

    @Test
    void deveAutenticarUsuario() {
        given()
                .contentType("application/json")
                .body(Map.of(
                        "email", "admin@bookstore.com",
                        "senha", "123456"
                ))
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("mensagem", equalTo("Login realizado com sucesso."));
    }

    @Test
    void deveListarLivrosAposLogin() {
        String c = login();

        given()
                .cookie("JSESSIONID", c)
                .get("/api/livros")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }

    @Test
    void deveRealizarVendaEReduzirEstoque() {
        String c = login();

        String isbn = "TESTE-" + System.nanoTime();

        Response livro = given()
                .cookie("JSESSIONID", c)
                .contentType("application/json")
                .body(Map.of(
                        "titulo", "Livro Teste Venda",
                        "autor", "Autor Teste",
                        "isbn", isbn,
                        "preco", 10.50,
                        "estoque", 5
                ))
                .post("/api/livros");

        livro.then().statusCode(201);

        Number livroId = livro.path("id");

        given()
                .cookie("JSESSIONID", c)
                .contentType("application/json")
                .body(Map.of(
                        "produtos", List.of(
                                Map.of(
                                        "livroId", livroId,
                                        "quantidade", 2
                                )
                        )
                ))
                .post("/api/vendas")
                .then()
                .statusCode(200)
                .body("mensagem", equalTo("Venda realizada com sucesso!"));

        given()
                .cookie("JSESSIONID", c)
                .get("/api/livros/" + livroId)
                .then()
                .statusCode(200)
                .body("estoque", equalTo(3));
    }
}
