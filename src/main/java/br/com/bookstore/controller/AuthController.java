package br.com.bookstore.controller;

import br.com.bookstore.model.Usuario;
import br.com.bookstore.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository usuarios;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> dados) {
        String nome = dados.get("nome");
        String email = dados.get("email");
        String senha = dados.get("senha");

        if (nome == null || nome.isBlank() ||
            email == null || email.isBlank() ||
            senha == null || senha.length() < 6) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Preencha os campos corretamente. A senha deve ter pelo menos 6 caracteres."));
        }

        if (usuarios.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Este e-mail já está cadastrado."));
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(encoder.encode(senha));
        usuario.setPerfil("USER");

        usuarios.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Cadastro realizado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> dados, HttpSession session) {
        Usuario usuario = usuarios.findByEmail(dados.getOrDefault("email", "")).orElse(null);

        if (usuario == null || !encoder.matches(dados.getOrDefault("senha", ""), usuario.getSenha())) {
            return ResponseEntity.status(401).body(Map.of("erro", "E-mail ou senha inválidos."));
        }

        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNome", usuario.getNome());

        return ResponseEntity.ok(Map.of("mensagem", "Login realizado com sucesso.", "nome", usuario.getNome()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("mensagem", "Logout realizado."));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        if (session.getAttribute("usuarioId") == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(Map.of("nome", session.getAttribute("usuarioNome")));
    }
}
