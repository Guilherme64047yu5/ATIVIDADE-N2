package br.com.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "livros")
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String titulo;
    @Column(nullable=false) private String autor;
    @Column(nullable=false, unique=true) private String isbn;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal preco;
    private Integer estoque;
    @ManyToOne @JoinColumn(name="categoria_id") private Categoria categoria;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getTitulo(){return titulo;} public void setTitulo(String titulo){this.titulo=titulo;}
    public String getAutor(){return autor;} public void setAutor(String autor){this.autor=autor;}
    public String getIsbn(){return isbn;} public void setIsbn(String isbn){this.isbn=isbn;}
    public BigDecimal getPreco(){return preco;} public void setPreco(BigDecimal preco){this.preco=preco;}
    public Integer getEstoque(){return estoque;} public void setEstoque(Integer estoque){this.estoque=estoque;}
    public Categoria getCategoria(){return categoria;} public void setCategoria(Categoria categoria){this.categoria=categoria;}
}
