package com.digarfo.digarfo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digarfo.digarfo.Model.Receita;
import com.digarfo.digarfo.Model.Usuario;
import com.digarfo.digarfo.Repository.ReceitaRepository;
	
	@RestController
	@RequestMapping("/receita")
	public class ReceitaController {
		@Autowired
		private ReceitaRepository receitaRepository;
		//GET todas as receitas
		@GetMapping
		public Iterable<Receita> getReceita(){
			return receitaRepository.findAll();
		}
		//GET por id	
		@GetMapping("/{id_receita}")
		public Receita buscaReceitaPorId(@PathVariable Long id_receita) {
			return receitaRepository.findById(id_receita).orElse(null);
		}
		//GET POR NOME
		@GetMapping("/nome/{nome_receita}")
		public List<Receita> buscarReceitaPorNome(@PathVariable String nome_receita) {
		    return receitaRepository.findByNome(nome_receita);
		}
		//GET por categoria
		@GetMapping("/categoria/{categoria}")
		public Iterable<Receita> buscarReceitaPorCateoria(@PathVariable String categoria) {
			return receitaRepository.findByCategoria(categoria);
		}
	 
		
		//CREATE nova receita
		@PostMapping
		public Receita adicionarReceita(@RequestBody Receita receita){
			return receitaRepository.save(receita);
		}
		//UPDATE receita por id
		@PutMapping("/{id}")
		public Receita atualizaReceita(@PathVariable Long id_receita, @RequestBody Receita receita) {
			receita.setId_receita(id_receita);
			return receitaRepository.save(receita);
		}
		//DELETE receita por id
		@DeleteMapping("/{id}")
		public void deletaReceita(@PathVariable Long id_receita) {
			receitaRepository.deleteById(id_receita);
			System.out.println("Receita deletada");
		}

}

