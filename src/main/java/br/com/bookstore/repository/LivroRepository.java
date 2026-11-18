package br.com.bookstore.repository;
import br.com.bookstore.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
