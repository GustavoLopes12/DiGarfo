package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="comentarios")
public class Comentario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_comentario;
	@Column(name="conteudo")
	private String conteudo;
	//construtores
	public Comentario() {
		//default
	}
	public Comentario(int id_comentario, String conteudo) {
		this.id_comentario = id_comentario;
		this.conteudo = conteudo;
	}
	
	
}
