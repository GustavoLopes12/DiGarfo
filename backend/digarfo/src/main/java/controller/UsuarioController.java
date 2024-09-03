package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Usuario;
import repository.UsuarioRepository;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	//GET todas os usuarios
	@GetMapping
	public Iterable<Usuario> getUsuario(){
		return usuarioRepository.findAll();
	}
	//GET por id	
	@GetMapping("/{id_usuario}")
	public Usuario buscaReceitaPorId(@PathVariable int id_usuario) {
		return usuarioRepository.findById(id_usuario).orElse(null);
	}
	//GET por nome
	@GetMapping("/nome/{nome_usuario}")
	public Iterable<Usuario> buscarReceitaPorNome(@PathVariable String nome_usuario) {
		return usuarioRepository.findByNome(nome_usuario);
	}
	//CREATE novo usuario
	@PostMapping
	public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	//UPDATE usuario por id
	@PutMapping("/{id}")
	public Usuario atualizaReceita(@PathVariable int id_usuario, @RequestBody Usuario usuario) {
		usuario.setId_usuario(id_usuario);
		return usuarioRepository.save(usuario);
	}
	//DELETE receita por id
	@DeleteMapping("/{id}")
	public void deletaReceita(@PathVariable int id_receita) {
		usuarioRepository.deleteById(id_receita);
		System.out.println("Receita deletada");
	}

}
