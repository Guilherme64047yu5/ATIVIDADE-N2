package br.com.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String nome;
    @Column(length=500) private String descricao;
    private String departamento;
    private Boolean ativa = true;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNome(){return nome;} public void setNome(String nome){this.nome=nome;}
    public String getDescricao(){return descricao;} public void setDescricao(String descricao){this.descricao=descricao;}
    public String getDepartamento(){return departamento;} public void setDepartamento(String departamento){this.departamento=departamento;}
    public Boolean getAtiva(){return ativa;} public void setAtiva(Boolean ativa){this.ativa=ativa;}
}
