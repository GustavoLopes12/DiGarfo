package model;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	//atributos
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_usuario;
	@Column(name= "nome_usuario")
	private String nome_usuario;
	@Column(name= "banido")
	private boolean banido;
	@Column(name= "img_user")
	private BufferedImage img_user;
	@Column(name= "email")
	private String email;
	@Column(name= "senha")
	private String senha;
	@Column(name= "descricao")
	private String descricao;
	//construtores
	public Usuario() {
		//default
	}
	
	public Usuario(int id_usuario, String nome_usuario, boolean banido, BufferedImage img_user, String email,
	String senha, String descricao) {
		this.id_usuario = id_usuario;
		this.nome_usuario = nome_usuario;
		this.banido = banido;
		this.img_user = img_user;
		this.email = email;
		this.senha = senha;
		this.descricao = descricao;
	}
	//getters e setters

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

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

	public BufferedImage getImg_user() {
		return img_user;
	}

	public void setImg_user(BufferedImage img_user) {
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
