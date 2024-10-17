package com.digarfo.digarfo.Model;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="adm")
public class Adm implements Serializable {
	@Id
	@Column(nullable=false)
	private String email;
	@Column(nullable=false)
	private String senha;
	
	//RELACIONAMENTO 1 PRA N COM USUARIO ADM BANI USUARIO
	@OneToMany(mappedBy = "adm", fetch=FetchType.EAGER)
	private List<Usuario> usuarios_banidos;
	
	//Relacionamento 1 pra n com receita ADM AVALIA RECEITA
	@OneToMany(mappedBy = "adm", fetch=FetchType.EAGER)
	private List<Receita> receitas_avaliadas;
	
	//construtores
	public Adm() {
		//default
	}
	public Adm(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	//getters and setters
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
	public List<Usuario> getUsuarios_banidos() {
		return usuarios_banidos;
	}
	public void setUsuarios_banidos(List<Usuario> usuarios_banidos) {
		this.usuarios_banidos = usuarios_banidos;
	}
	public List<Receita> getReceitas_avaliadas() {
		return receitas_avaliadas;
	}
	public void setReceitas_avaliadas(List<Receita> receitas_avaliadas) {
		this.receitas_avaliadas = receitas_avaliadas;
	}
	
}
