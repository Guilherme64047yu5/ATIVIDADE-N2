package br.com.bookstore.config;
import br.com.bookstore.model.*; import br.com.bookstore.repository.*;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import java.math.BigDecimal;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner iniciar(UsuarioRepository usuarios,CategoriaRepository categorias,LivroRepository livros){
  return args->{
   if(usuarios.findByEmail("admin@bookstore.com").isEmpty()){Usuario u=new Usuario();u.setNome("Administrador");u.setEmail("admin@bookstore.com");u.setSenha(new BCryptPasswordEncoder().encode("123456"));usuarios.save(u);}
   if(categorias.count()==0){Categoria c=new Categoria();c.setNome("Romance");c.setDescricao("Livros de romance");c.setDepartamento("Ficção");c.setAtiva(true);categorias.save(c);}
   if(livros.count()==0){Livro l=new Livro();l.setTitulo("Dom Casmurro");l.setAutor("Machado de Assis");l.setIsbn("9788594318083");l.setPreco(new BigDecimal("29.90"));l.setEstoque(10);l.setCategoria(categorias.findAll().get(0));livros.save(l);}
  };
 }
}
