package com.digarfo.digarfo.Model;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
	@Column(nullable=false)
	private boolean banido;
	@Column(nullable=true)
	private String img_user;//caminho da imagem né
	@Column(nullable=false)
	private String senha;
	@Column(nullable=true)
	private String descricao;
	@Column(nullable=true)
	private String motivo_banimento;
	//relaçionamento 1 pra n com receita CRIACAO DE RECEITA
	@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	private List<Receita> receitas_usuario;
	
	//relacionamento n pra n com receita USUARIO FAVORITA RECEITA
	//@ManyToMany(mappedBy="usuarios")
	//private Set<Receita> receitas_fav;
	
	//relacionamento n pra n com receita USUARIO DENUNCIA RECEITA
	
	//relacionamento n pra n com receita USUARIO AVALIA RECEITA
	
	//RELACIONAMENTO N PRA 1 COM ADM, ADM BANI USUARIO
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_adm", nullable=true)
	private Adm adm;
	
	//relacionamento 1 pra n com comentario USUARIO FAZ COMENTARIO
	//@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
	//private List<Comentario> comentarios;
	
	//relacionamento n pra n com comentario USUARIO DENUNCIA COMENTARIO
	
	//construtores
	public Usuario() {
		//default
	}
	public Usuario(String nome_usuario, boolean banido, String img_user, String email,
	String senha, String descricao) {
		this.nome_usuario = nome_usuario;
		this.banido = banido;
		this.img_user = img_user;
		this.email = email;
		this.senha = senha;
		this.descricao = descricao;
	}
	//getters e setters

	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

	public boolean isBanido() {
		return banido;
	}

	public void setBanido(boolean banido) {
		this.banido = banido;
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
	public String getMotivo_banimento() {
		return motivo_banimento;
	}
	public void setMotivo_banimento(String motivo_banimento) {
		this.motivo_banimento = motivo_banimento;
	}
	public List<Receita> getReceitas_usuario() {
		return receitas_usuario;
	}
	public void setReceitas_usuario(List<Receita> receitas_usuario) {
		this.receitas_usuario = receitas_usuario;
	}
	/*public Set<Receita> getReceitas_fav() {
		return receitas_fav;
	}
	public void setReceitas_fav(Set<Receita> receitas_fav) {
		this.receitas_fav = receitas_fav;
	}*/
	public Adm getAdm() {
		return adm;
	}
	public void setAdm(Adm adm) {
		this.adm = adm;
	}
	/*public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	*/
}

