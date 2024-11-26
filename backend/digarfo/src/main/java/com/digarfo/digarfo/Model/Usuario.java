package com.digarfo.digarfo.Model;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	//atributos
	@Id
	@Column(nullable=false)
	private String email;
	@Column(nullable=false)
	private String nome_usuario;
	@Column(nullable=true)
	private String img_user;//caminho da imagem né
	@Column(nullable=false)
	private String senha;
	@Column(nullable=true)
	private String descricao;
	//relaçionamento 1 pra n com receita CRIACAO DE RECEITA
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	@JsonManagedReference(value="usuario_receita")
	private Set<Receita> receitas_usuario;
	
	//construtores
	public Usuario() {
		//default
	}
	public Usuario(String nome_usuario, String img_user, String email,
	String senha, String descricao, Set<Receita> receitas_usuario) {
		this.nome_usuario = nome_usuario;
		this.img_user = img_user;
		this.email = email;
		this.senha = senha;
		this.descricao = descricao;
		this.receitas_usuario = receitas_usuario;
	}
	//getters e setters

	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

	public String getImg_user() {
		return img_user;
	}

	public void setImg_user(String img_user) {
		this.img_user = img_user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Set<Receita> getReceitas_usuario() {
		return receitas_usuario;
	}
	public void setReceitas_usuario(Set<Receita> receitas_usuario) {
		this.receitas_usuario = receitas_usuario;
	}
}

