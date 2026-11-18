package br.com.bookstore.service;

import br.com.bookstore.model.Livro;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class LivroService {
    public void validar(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().isBlank())
            throw new IllegalArgumentException("O título é obrigatório.");
        if (livro.getAutor() == null || livro.getAutor().isBlank())
            throw new IllegalArgumentException("O autor é obrigatório.");
        if (livro.getIsbn() == null || livro.getIsbn().isBlank())
            throw new IllegalArgumentException("O ISBN é obrigatório.");
        if (livro.getPreco() == null || livro.getPreco().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        if (livro.getEstoque() != null && livro.getEstoque() < 0)
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
    }
}
