package br.com.bookstore.controller;
import br.com.bookstore.model.*;
import br.com.bookstore.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/livros")
public class LivroController {
 private final LivroRepository livros; private final CategoriaRepository categorias;
 public LivroController(LivroRepository livros,CategoriaRepository categorias){this.livros=livros;this.categorias=categorias;}
 @GetMapping public List<Livro> listar(){return livros.findAll();}
 @GetMapping("/{id}") public ResponseEntity<Livro> buscar(@PathVariable Long id){return livros.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
 @PostMapping public ResponseEntity<?> criar(@RequestBody Livro livro){
  if(livro.getTitulo()==null||livro.getTitulo().isBlank()||livro.getAutor()==null||livro.getIsbn()==null||livro.getPreco()==null)
   return ResponseEntity.badRequest().body(Map.of("erro","Preencha título, autor, ISBN e preço."));
  if(livro.getCategoria()!=null && livro.getCategoria().getId()!=null) livro.setCategoria(categorias.findById(livro.getCategoria().getId()).orElse(null));
  return ResponseEntity.status(201).body(livros.save(livro));
 }
 @PutMapping("/{id}") public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Livro dados){
  return livros.findById(id).map(l->{l.setTitulo(dados.getTitulo());l.setAutor(dados.getAutor());l.setIsbn(dados.getIsbn());l.setPreco(dados.getPreco());l.setEstoque(dados.getEstoque());if(dados.getCategoria()!=null&&dados.getCategoria().getId()!=null)l.setCategoria(categorias.findById(dados.getCategoria().getId()).orElse(null));return ResponseEntity.ok(livros.save(l));}).orElse(ResponseEntity.notFound().build());
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id){if(!livros.existsById(id))return ResponseEntity.notFound().build();livros.deleteById(id);return ResponseEntity.noContent().build();}
}
