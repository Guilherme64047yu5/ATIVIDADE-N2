package br.com.bookstore.controller;

import br.com.bookstore.model.Venda;
import br.com.bookstore.repository.VendaRepository;
import br.com.bookstore.service.VendaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final VendaRepository vendas;

    public VendaController(VendaService vendaService, VendaRepository vendas) {
        this.vendaService = vendaService;
        this.vendas = vendas;
    }

    @PostMapping
    public ResponseEntity<?> realizarVenda(
            @RequestBody Map<String, List<Map<String, Object>>> dados,
            HttpSession session) {

        Long usuarioId = (Long) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return ResponseEntity.status(401).body(Map.of("erro", "Faça login para realizar uma venda."));
        }

        try {
            Venda venda = vendaService.realizarVenda(usuarioId, dados.get("produtos"));

            return ResponseEntity.ok(Map.of(
                    "mensagem", "Venda realizada com sucesso!",
                    "vendaId", venda.getId(),
                    "total", venda.getTotal()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarVendas(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(vendas.findByUsuarioIdOrderByDataDesc(usuarioId));
    }
}
