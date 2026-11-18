package br.com.bookstore.repository;

import br.com.bookstore.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByUsuarioIdOrderByDataDesc(Long usuarioId);
}
