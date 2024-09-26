package com.digarfo.digarfo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.digarfo.digarfo.Model.Usuario;
import com.digarfo.digarfo.Repository.UsuarioRepository;
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	//UPDATE usuario por id(email)
	@PutMapping("/{email}")
	public Usuario atualizaUsuario(@PathVariable String email, @RequestBody Usuario usuario) {
		usuario.setEmail(email);
		return usuarioRepository.save(usuario);
	}
	//DELETE usuario receita por id(email)
	@DeleteMapping("/{email}")
	public void deletaUsuario(@PathVariable String email) {
		usuarioRepository.deleteById(email);
	}
	
	

}
