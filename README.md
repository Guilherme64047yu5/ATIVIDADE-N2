# BookStore — Sistema de Vendas de Livros

**Integrante:** Guilherme Costa da Silva  
**Disciplina:** Testes de Software — Reinaldo

## Proposta
Sistema web de gerenciamento e vendas de livros para uma pequena livraria. Permite cadastrar livros, categorias e usuários, registrar vendas, calcular valores e controlar automaticamente o estoque dos produtos vendidos.

## Funcionalidades
- Login e logout com controle de sessão.
- Senhas protegidas com BCrypt.
- Cadastro de usuários.
- CRUD completo de livros.
- CRUD completo de categorias.
- Gerenciamento de usuários.
- Cadastro e consulta de vendas.
- Carrinho para adicionar e remover livros.
- Cálculo automático de subtotais e total da venda.
- Verificação de estoque antes da venda.
- Baixa automática do estoque após a confirmação.
- Histórico de vendas realizadas.
- APIs REST protegidas por sessão.
- Banco H2 persistido em `./data/bookstoredb`.

## Tecnologias utilizadas
- Java 17
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- HTML, CSS e JavaScript
- JUnit 5
- Selenium 4
- REST-assured

## Pré-requisitos
- JDK 17 ou superior.
- Maven instalado.
- Google Chrome para os testes Selenium.

## Como executar
Na pasta do projeto, execute:

    mvn spring-boot:run

Acesse `http://localhost:8080/login.html`.

**Login inicial:**
- E-mail: `admin@bookstore.com`
- Senha: `123456`

**Console H2:** `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:file:./data/bookstoredb`
- Usuário: `sa`
- Senha: em branco

## Como executar os testes

Execute:

    mvn clean test

O projeto possui 8 testes automatizados.

### JUnit — 3 testes
Classe: `LivroServiceUnitTest`

- Livro com dados válidos.
- Rejeição de livro sem título.
- Rejeição de preço zero ou negativo.

### REST-assured — 3 testes
Classe: `ApiRestAssuredTest`

- Autenticação pela API.
- Listagem de livros após login.
- Realização de venda e verificação da redução automática do estoque.

### Selenium — 2 testes
Classe: `SeleniumE2ETest`

- Verificação da tela de login.
- Verificação do acesso ao painel após autenticação.

Os testes Selenium utilizam WebDriverManager para configurar o ChromeDriver.

## Principais endpoints

| Método | Endpoint | Finalidade |
|---|---|---|
| POST | `/api/auth/login` | Realizar login |
| POST | `/api/auth/logout` | Encerrar sessão |
| POST | `/api/auth/cadastro` | Cadastrar usuário |
| GET/POST | `/api/livros` | Listar e cadastrar livros |
| GET/PUT/DELETE | `/api/livros/{id}` | Consultar, editar e excluir livros |
| GET/POST | `/api/categorias` | Listar e cadastrar categorias |
| GET/PUT/DELETE | `/api/categorias/{id}` | Consultar, editar e excluir categorias |
| GET/POST | `/api/usuarios` | Listar e cadastrar usuários |
| PUT/DELETE | `/api/usuarios/{id}` | Editar e excluir usuários |
| POST | `/api/vendas` | Registrar uma venda |
| GET | `/api/vendas` | Consultar histórico de vendas |

## Resultado dos testes

Na última execução de `mvn clean test`, os 8 testes automatizados passaram, sem falhas ou erros.