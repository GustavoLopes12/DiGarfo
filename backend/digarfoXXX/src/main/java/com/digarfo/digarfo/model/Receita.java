package com.digarfo.digarfo.model;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="receita")
public class Receita implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_receita;
	@Column(name= "nome_receita")
	private String nome_receita;
	@Column(name= "custo")
	private String custo;
	@Column(name= "categoria")
	private String categoria;
	@Column(name= "dificuldade")
	private String dificuldade;
	@Column(name= "tempo_prep")
	private String tempo_prep;
	@Column(name= "ingredientes")
	private String ingredientes;
	@Column(name= "modo_prep")
	private String modo_prep;
	@Column(name= "img_receita")
	private BufferedImage img_receita;
	@Column(name= "aprovacao")
	private boolean aprovacao;
	//construtores
	public Receita() {
		//default
	}
	public Receita(int id_receita, String nome_receita, String custo, String categoria, String dificuldade,
		String tempo_prep, String ingredientes, String modo_prep, BufferedImage img_receita, boolean aprovacao) {
		this.id_receita = id_receita;
		this.nome_receita = nome_receita;
		this.custo = custo;
		this.categoria = categoria;
		this.dificuldade = dificuldade;
		this.tempo_prep = tempo_prep;
		this.ingredientes = ingredientes;
		this.modo_prep = modo_prep;
		this.img_receita = img_receita;
		this.aprovacao = aprovacao;
	}
	//getters e setters
	public int getId_receita() {
		return id_receita;
	}
	public void setId_receita(int id_receita) {
		this.id_receita = id_receita;
	}
	public String getNome_receita() {
		return nome_receita;
	}
	public void setNome_receita(String nome_receita) {
		this.nome_receita = nome_receita;
	}
	public String getCusto() {
		return custo;
	}
	public void setCusto(String custo) {
		this.custo = custo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDificuldade() {
		return dificuldade;
	}
	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}
	public String getTempo_prep() {
		return tempo_prep;
	}
	public void setTempo_prep(String tempo_prep) {
		this.tempo_prep = tempo_prep;
	}
	public String getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}
	public String getModo_prep() {
		return modo_prep;
	}
	public void setModo_prep(String modo_prep) {
		this.modo_prep = modo_prep;
	}
	public BufferedImage getImg_receita() {
		return img_receita;
	}
	public void setImg_receita(BufferedImage img_receita) {
		this.img_receita = img_receita;
	}
	public boolean isAprovacao() {
		return aprovacao;
	}
	public void setAprovacao(boolean aprovacao) {
		this.aprovacao = aprovacao;
	}
}
