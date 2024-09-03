package com.digarfo.digarfo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digarfo.digarfo.model.Comentario;
import com.digarfo.digarfo.model.Usuario;
import com.digarfo.digarfo.repository.ComentarioRepository;

@RestController
@RequestMapping("/Comentario")
public class ComentarioController {
	@Autowired
	private ComentarioRepository comentarioRepository;
	
		//GET todas os comentarios
		@GetMapping
		public Iterable<Comentario> getComentario(){
			return comentarioRepository.findAll();
		}
		//GET por id	
		@GetMapping("/comentarioForId/{id_comentario}")
		public Comentario buscaComentarioPorId(@PathVariable int id_comentario) {
			return comentarioRepository.findById(id_comentario).orElse(null);
		}
		//CREATE novo comentario
		@PostMapping
		public Comentario adicionarComentario(@RequestBody Comentario comentario) {
			return comentarioRepository.save(comentario);
		}
		//UPDATE usuario por id
		@PutMapping("/{id_comentario}")
		public Comentario atualizaComentario(@PathVariable int id_comentario, @RequestBody Comentario comentario) {
			comentario.setId_comentario(id_comentario);
			return comentarioRepository.save(comentario);
		}
		//DELETE receita por id
		@DeleteMapping("/{id}")
		public void deletaComentario(@PathVariable int id_comentario) {
			comentarioRepository.deleteById(id_comentario);
			System.out.println("Comentario deletado");
		}
}
