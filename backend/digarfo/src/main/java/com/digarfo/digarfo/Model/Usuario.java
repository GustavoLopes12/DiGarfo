package com.digarfo.digarfo.Model;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Usuario implements Serializable {
	//atributos
	@Id
	private String email;
	private String nome_usuario;
	private boolean banido;
	private String img_user;//caminho da imagem né
	private String senha;
	private String descricao;
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
}

