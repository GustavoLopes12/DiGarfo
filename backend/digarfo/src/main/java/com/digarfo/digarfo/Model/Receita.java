package com.digarfo.digarfo.Model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="receita")
public class Receita implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_receita;
	@Column(nullable=false)
	private String nome_receita;
	@Column(nullable=false)
	private String custo;
	@Column(nullable=false)
	private String categoria;
	@Column(nullable=false)
	private String dificuldade;
	@Column(nullable=false)
	private String tempo_prep;
	@Column(nullable=false, length = 999999999)
	private String ingredientes;
	@Column(nullable=false, length = 999999999)
	private String modo_prep;
	@Column(nullable=true)
	private String img_receita;
	@Column(nullable=false)
	private boolean aprovada;
	@Column(nullable=true, length = 500)
	private String motivo_desaprovacao;
	
	//relacionamento n pra 1 com usuario CRIACAO DE RECEITA
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_autor", nullable=false)
	@JsonBackReference(value="usuario_receita")
	private Usuario usuario;
	
	//relacionamento n pra 1 com adm ADM AVALIA RECEITA
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_adm", nullable=true)
	private Adm adm;
	
	//construtores
	public Receita() {
		//default
	}
	public Receita(Long id_receita, String nome_receita, String custo, String categoria, String dificuldade,
		String tempo_prep, String ingredientes, String modo_prep, String img_receita, boolean aprovada, String motivo_desaprovacao, Usuario usuario, Adm adm) {
		this.id_receita = id_receita;
		this.nome_receita = nome_receita;
		this.custo = custo;
		this.categoria = categoria;
		this.dificuldade = dificuldade;
		this.tempo_prep = tempo_prep;
		this.ingredientes = ingredientes;
		this.modo_prep = modo_prep;
		this.img_receita = img_receita;
		this.aprovada = aprovada;
		this.motivo_desaprovacao = motivo_desaprovacao;
		this.usuario = usuario;
		this.adm = adm;
	}
	//getters e setters
	public Long getId_receita() {
		return id_receita;
	}
	public void setId_receita(Long id_receita) {
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
	public String getImg_receita() {
		return img_receita;
	}
	public void setImg_receita(String img_receita) {
		this.img_receita = img_receita;
	}
	public boolean getAprovada() {
		return aprovada;
	}
	public void setAprovada(boolean aprovada) {
		this.aprovada = aprovada;
	}
	public String getMotivo_desaprovacao() {
		return motivo_desaprovacao;
	}
	public void setMotivo_desaprovacao(String motivo_desaprovacao) {
		this.motivo_desaprovacao = motivo_desaprovacao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Adm getAdm() {
		return adm;
	}
	public void setAdm(Adm adm) {
		this.adm = adm;
	}
}

