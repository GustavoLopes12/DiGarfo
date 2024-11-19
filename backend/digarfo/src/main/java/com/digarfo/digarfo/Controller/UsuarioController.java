package com.digarfo.digarfo.Controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digarfo.digarfo.Model.Usuario;
import com.digarfo.digarfo.Repository.UsuarioRepository;
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static String diretorio = System.getProperty("user.home") + "\\DiGarfo\\imagens\\imagem_perfil\\"; //salvar imagem aqui
	
	//login
	@PostMapping("/login")
	public Usuario login(@RequestBody Usuario usuario){
		Usuario foundUser = usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if (foundUser != null) {
			return foundUser;
		}else {
			Usuario emptyUser = new Usuario();
			return emptyUser;
		}
	}
	
	//get por receita
	@GetMapping("/receita/{idReceita}")
	public ResponseEntity<Usuario> buscarUsuarioPorReceita(@PathVariable Long idReceita) {
	    Usuario usuario = usuarioRepository.findUsuarioByReceitaId(idReceita);
	    if (usuario == null) {
	        System.out.println("Usuario não encontrado para a receita com ID: " + idReceita);
	        return ResponseEntity.notFound().build();
	    }
	    System.out.println("Usuario encontrado: " + usuario.getNome_usuario());
	    return ResponseEntity.ok(usuario);
	}
	
	//GET por nome
	@GetMapping("/{nome_usuario}")
	public Iterable<Usuario> buscarUsuarioPorNome(@RequestParam String nome_usuario) {
	return usuarioRepository.findByNome_usuario(nome_usuario);
	} 
	
	//GET todas os usuarios
	@GetMapping
	public Iterable<Usuario> getUsuario(){
		return usuarioRepository.findAll();
	}
	//GET por id(email)	
	@GetMapping("/email/{email}")
	public Usuario buscaUsuarioPorEmail(@PathVariable String email) {
		return usuarioRepository.findById(email).orElse(null);
	}
	
	//CREATE novo usuario
	@PostMapping
	public Usuario adicionarUsuario(@RequestBody Usuario usuario/*@RequestParam String email, @RequestParam String nome_usuario, 
	@RequestParam boolean banido, @RequestParam("file") MultipartFile arquivo, @RequestParam String senha, 
	@RequestParam String descricao*/) {
		/*Usuario usuario = new Usuario(nome_usuario, banido, null, email, senha, descricao);
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(diretorio+String.valueOf(usuario.getEmail())+arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				usuario.setImg_user(String.valueOf(usuario.getEmail())+arquivo.getOriginalFilename());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}*/
		
		return usuarioRepository.save(usuario);
	}
	//UPDATE usuario por id(email)
	@PutMapping("/{email}")
	public Usuario atualizaUsuario(@PathVariable String email, @RequestBody Usuario usuario) {
		usuario.setEmail(email);
		return usuarioRepository.save(usuario);
	}
	//UPDATE usuario com imagem de perfil indo junto
	@PutMapping("/attUserWithImageUser/{email}")
	public Usuario atualizarUsuarioWithImage(@PathVariable String email, @RequestBody Usuario usuario,  @RequestParam("file") MultipartFile arquivo) {
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(diretorio+String.valueOf(usuario.getEmail())+arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				usuario.setImg_user(String.valueOf(usuario.getEmail())+arquivo.getOriginalFilename());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		//salvando
		usuario.setEmail(email);
		return usuarioRepository.save(usuario);
	}
	//DELETE usuario usuario por id(email)
	@DeleteMapping("/{email}")
	public ResponseEntity<String> deletaUsuario(@PathVariable String email) {
		try {
			usuarioRepository.deleteById(email);
			System.out.println("Usuario deletado");
	        return ResponseEntity.ok("Usuario deletado com sucesso"); // Retorna status 200 com mensagem
		}catch(Exception e) {
			System.out.println("Erro ao deletar usuario: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar receita"); // Retorna status 500 em caso de erro
		}
	}

	/*
	@DeleteMapping("/{email}")
	public void deletaUsuario(@PathVariable String email) {
		usuarioRepository.deleteById(email);
	}
	*/
	
}
