package br.com.bookstore.controller;
import br.com.bookstore.model.Categoria; import br.com.bookstore.repository.CategoriaRepository;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/categorias")
public class CategoriaController {
 private final CategoriaRepository repo; public CategoriaController(CategoriaRepository repo){this.repo=repo;}
 @GetMapping public List<Categoria> listar(){return repo.findAll();}
 @GetMapping("/{id}") public ResponseEntity<Categoria> buscar(@PathVariable Long id){return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
 @PostMapping public ResponseEntity<?> criar(@RequestBody Categoria c){if(c.getNome()==null||c.getNome().isBlank())return ResponseEntity.badRequest().body(Map.of("erro","Nome obrigatório."));return ResponseEntity.status(201).body(repo.save(c));}
 @PutMapping("/{id}") public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Categoria d){return repo.findById(id).map(c->{c.setNome(d.getNome());c.setDescricao(d.getDescricao());c.setDepartamento(d.getDepartamento());c.setAtiva(d.getAtiva());return ResponseEntity.ok(repo.save(c));}).orElse(ResponseEntity.notFound().build());}
 @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id){if(!repo.existsById(id))return ResponseEntity.notFound().build();repo.deleteById(id);return ResponseEntity.noContent().build();}
}
