package com.digarfo.digarfo.Model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Comentario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_comentario;
	private String conteudo;
	//construtores
	public Comentario() {
		//default
	}
	public Comentario(long id_comentario, String conteudo) {
		this.id_comentario = id_comentario;
		this.conteudo = conteudo;
	}
	public long getId_comentario() {
		return id_comentario;
	}
	public void setId_comentario(long id_comentario) {
		this.id_comentario = id_comentario;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}

