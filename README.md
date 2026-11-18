# BookStore â€” Sistema de Vendas de Livros

**Integrantes:** preencher com os nomes completos dos integrantes antes da entrega.  
**Disciplina:** Testes de Software â€” Prof. Reinaldo Jr.  
**Proposta:** sistema web para gerenciamento de uma pequena livraria, com autenticaÃ§Ã£o e operaÃ§Ãµes de cadastro, consulta, ediÃ§Ã£o e exclusÃ£o.

## Funcionalidades
- Login e logout com sessÃ£o; senhas armazenadas com BCrypt.
- CRUD de livros: tÃ­tulo, autor, ISBN, preÃ§o, estoque e categoria.
- CRUD de categorias: nome, descriÃ§Ã£o, departamento e situaÃ§Ã£o.
- CRUD de usuÃ¡rios: nome, e-mail, senha e perfil.
- APIs REST protegidas por sessÃ£o, exceto autenticaÃ§Ã£o.
- Banco H2 relacional persistido em `./data/bookstoredb`.

## Tecnologias
Java 17, Spring Boot 3.5.6, Spring Web, Spring Data JPA, Hibernate, H2, HTML, CSS, JavaScript, JUnit 5, Selenium 4 e REST-assured.

## PrÃ©-requisitos
- JDK 17 instalado e configurado no PATH.
- Maven 3.9+ (ou usar `mvnw`, caso adicionado pelo ambiente).
- Google Chrome instalado para executar os testes Selenium.

## Como executar
1. Abra a pasta do projeto no VS Code ou NetBeans.
2. No terminal, dentro da pasta que contÃ©m `pom.xml`, execute:
   ```bash
   mvn spring-boot:run
   ```
3. Acesse `http://localhost:8080/login.html`.
4. Login inicial: **admin@bookstore.com** / **123456**.

O banco H2 Ã© criado automaticamente na primeira execuÃ§Ã£o. O console pode ser acessado em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/bookstoredb`, usuÃ¡rio `sa`, senha em branco).

## Como executar os testes
Execute todos os testes:
```bash
mvn test
```
- **JUnit:** `LivroServiceUnitTest` contÃ©m 3 testes unitÃ¡rios de atributos e regras bÃ¡sicas.
- **REST-assured:** `ApiRestAssuredTest` contÃ©m 2 testes de autenticaÃ§Ã£o e listagem autenticada.
- **Selenium:** `SeleniumE2ETest` contÃ©m 2 testes de interface: tela de login e entrada no painel.

Os testes Selenium abrem o Chrome automaticamente usando WebDriverManager. Ã‰ necessÃ¡rio ter o Chrome instalado e permitir o download do driver.

## Endpoints principais
| MÃ©todo | Endpoint | Finalidade |
|---|---|---|
| POST | `/api/auth/login` | autenticar |
| POST | `/api/auth/logout` | sair |
| GET/POST | `/api/livros` | listar/criar livros |
| GET/PUT/DELETE | `/api/livros/{id}` | consultar/editar/excluir livro |
| GET/POST | `/api/categorias` | listar/criar categorias |
| GET/PUT/DELETE | `/api/categorias/{id}` | consultar/editar/excluir categoria |
| GET/POST | `/api/usuarios` | listar/criar usuÃ¡rios |
| PUT/DELETE | `/api/usuarios/{id}` | editar/excluir usuÃ¡rio |

## ObservaÃ§Ãµes para a entrega
- Preencha os nomes dos integrantes.
- Execute `mvn test` no computador da apresentaÃ§Ã£o e corrija qualquer problema de ambiente antes de gravar.
- Grave o vÃ­deo demonstrando a execuÃ§Ã£o real dos testes; o README e o cÃ³digo nÃ£o substituem o vÃ­deo solicitado.


