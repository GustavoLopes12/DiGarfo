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
import org.springframework.web.bind.annotation.RestController;
import com.digarfo.digarfo.Model.Adm;
import com.digarfo.digarfo.Model.Usuario;
import com.digarfo.digarfo.Repository.AdmRepository;

@RestController
@RequestMapping("/adm")
public class AdmController {
	//adm tem os endpoints de usuario pois o adm domina sobre os usuarios
	@Autowired
	private AdmRepository admRepository;
		//login
		@PostMapping("/loginAdm")
		public ResponseEntity<String> login(@RequestBody Adm adm){
			Adm foundAdm = admRepository.findByEmailAndSenha(adm.getEmail(), adm.getSenha());
			if (foundAdm != null) {
				return ResponseEntity.ok("Login feito");
			}else {
				return ResponseEntity.status(401).body("Login nao realizado");
			}
		}
	  	//GET todos os adms
		@GetMapping
		public Iterable<Adm> getAdm(){
			return admRepository.findAll();
		}
		//GET adm por id(email)	
		@GetMapping("/{email}")
		public Adm buscaAdmPorId(@PathVariable String email) {
			return admRepository.findById(email).orElse(null);
		}
		//CREATE adm
		@PostMapping
		public Adm adicionarAdm(@RequestBody Adm adm) {
			return admRepository.save(adm);
		}
		@PutMapping("/{email}")
		public Adm atualizaAdm(@PathVariable String email, @RequestBody Adm adm) {
			adm.setEmail(email);
			return admRepository.save(adm);
		}
		@DeleteMapping("/{email}")
		public void deletaAdm(@PathVariable String email) {
			admRepository.deleteById(email);
		}
	/*@Autowired
	private UsuarioRepository usuarioRepository;
	    //GET todas os usuarios
		@GetMapping("/allUsers")
		public Iterable<Usuario> getUsuario(){
			return usuarioRepository.findAll();
		}
		//GET user por id	
		@GetMapping("/userForId/{id_usuario}")
		public Usuario buscaUsuarioPorId(@PathVariable int id_usuario) {
			return usuarioRepository.findById(id_usuario).orElse(null);
		}
		//GET user por nome
		@GetMapping("/userForName/{nome_usuario}")
		public Iterable<Usuario> buscarUsuarioPorNome(@PathVariable String nome_usuario) {
			return usuarioRepository.findByNome(nome_usuario);
		}
		//DELETE user por id
		@DeleteMapping("/deleteUserForId/{id}")
		public void deletaUsuario(@PathVariable int id_usuario) {
			usuarioRepository.deleteById(id_usuario);
			System.out.println("Usuario deletado");
		}
		//UPDATE usuario por id
		@PutMapping("/attUserForId/{id}")
		public Usuario atualizaUsuario(@PathVariable int id_usuario, @RequestBody Usuario usuario) {
			usuario.setId_usuario(id_usuario);
			return usuarioRepository.save(usuario);
		}
	@Autowired
	private ReceitaRepository receitaRepository;
			//UPDATE receita por id
			@PutMapping("attReceitaForId/{id}")
			public Receita atualizaReceita(@PathVariable int id_receita, @RequestBody Receita receita) {
				receita.setId_receita(id_receita);
				return receitaRepository.save(receita);
			}
			//DELETE receita por id
			@DeleteMapping("deletaReceitaForId/{id}")
			public void deletaReceita(@PathVariable int id_receita) {
				receitaRepository.deleteById(id_receita);
				System.out.println("Receita deletada");
			}*/
	//deletar receita 
}//fim class

