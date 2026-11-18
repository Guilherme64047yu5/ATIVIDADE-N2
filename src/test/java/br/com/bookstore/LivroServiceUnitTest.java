package br.com.bookstore;

import br.com.bookstore.model.Livro;
import br.com.bookstore.service.LivroService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class LivroServiceUnitTest {
    private final LivroService service = new LivroService();

    private Livro livroValido() {
        Livro livro = new Livro();
        livro.setTitulo("O Hobbit");
        livro.setAutor("J. R. R. Tolkien");
        livro.setIsbn("9788595084742");
        livro.setPreco(new BigDecimal("49.90"));
        livro.setEstoque(10);
        return livro;
    }

    @Test void deveAceitarLivroComDadosValidos() {
        assertDoesNotThrow(() -> service.validar(livroValido()));
    }

    @Test void deveRejeitarLivroSemTitulo() {
        Livro livro = livroValido();
        livro.setTitulo(" ");
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class, () -> service.validar(livro));
        assertEquals("O título é obrigatório.", erro.getMessage());
    }

    @Test void deveRejeitarPrecoZeroOuNegativo() {
        Livro livro = livroValido();
        livro.setPreco(BigDecimal.ZERO);
        assertThrows(IllegalArgumentException.class, () -> service.validar(livro));
    }
}
