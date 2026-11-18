package br.com.bookstore.controller;
import br.com.bookstore.model.Usuario; import br.com.bookstore.repository.UsuarioRepository;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;
@RestController @RequestMapping("/api/usuarios")
public class UsuarioController {
 private final UsuarioRepository repo; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public UsuarioController(UsuarioRepository repo){this.repo=repo;}
 @GetMapping public List<Map<String,Object>> listar(){return repo.findAll().stream().map(this::publico).toList();}
 @PostMapping public ResponseEntity<?> criar(@RequestBody Usuario u){if(u.getNome()==null||u.getEmail()==null||u.getSenha()==null)return ResponseEntity.badRequest().body(Map.of("erro","Nome, e-mail e senha são obrigatórios."));u.setSenha(encoder.encode(u.getSenha()));return ResponseEntity.status(201).body(publico(repo.save(u)));}
 @PutMapping("/{id}") public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Usuario d){return repo.findById(id).map(u->{u.setNome(d.getNome());u.setEmail(d.getEmail());if(d.getSenha()!=null&&!d.getSenha().isBlank())u.setSenha(encoder.encode(d.getSenha()));return ResponseEntity.ok(publico(repo.save(u)));}).orElse(ResponseEntity.notFound().build());}
 @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id){if(!repo.existsById(id))return ResponseEntity.notFound().build();repo.deleteById(id);return ResponseEntity.noContent().build();}
 private Map<String,Object> publico(Usuario u){return Map.of("id",u.getId(),"nome",u.getNome(),"email",u.getEmail(),"perfil",u.getPerfil());}
}
