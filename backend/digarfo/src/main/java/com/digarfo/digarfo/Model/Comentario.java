package com.digarfo.digarfo.Model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comentario")
public class Comentario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_comentario;
	@Column(nullable=false)
	private String conteudo;
	@Column(nullable=false)
	private Date data_coment;
	
	//relacionamento n pra 1 com usuario USUARIO FAZ COMENTARIO
	//@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="id_usuario_fk", nullable=false)
	//private Usuario usuario;
	
	//relacionamento n pra 1 com receita RECEITA TEM COMENTARIO
	//@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="id_receita_fk", nullable=false)
	//private Receita receita;
	
	//construtores
	public Comentario() {
		//default
	}
	public Comentario(long id_comentario, String conteudo, Date data_coment) {
		this.id_comentario = id_comentario;
		this.conteudo = conteudo;
		this.data_coment = data_coment;
	}
	//getters e setters
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
	public Date getData_coment() {
		return data_coment;
	}
	public void setData_coment(Date data_coment) {
		this.data_coment = data_coment;
	}
}

