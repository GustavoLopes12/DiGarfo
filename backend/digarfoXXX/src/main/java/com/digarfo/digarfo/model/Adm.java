package com.digarfo.digarfo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="adm")
public class Adm implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_adm;
	@Column(name= "email")
	private String email;
	@Column(name= "senha")
	private String senha;
	//construtores
	public Adm() {
		//default
	}
	public Adm(int id_adm, String email, String senha) {
		this.id_adm = id_adm;
		this.email = email;
		this.senha = senha;
	}
	//getters and setters
	public int getId_adm() {
		return id_adm;
	}
	public void setId_adm(int id_adm) {
		this.id_adm = id_adm;
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
}
