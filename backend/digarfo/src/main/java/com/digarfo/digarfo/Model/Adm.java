package com.digarfo.digarfo.Model;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
public class Adm implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String email;
	private String senha;
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
}
