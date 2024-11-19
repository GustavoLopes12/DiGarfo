package com.digarfo.digarfo.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digarfo.digarfo.Model.Receita;
import com.digarfo.digarfo.Model.Usuario;
import com.digarfo.digarfo.Repository.ReceitaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
	
	@RestController
	@RequestMapping("/receita")
	public class ReceitaController {
		private static String caminhoimg = System.getProperty("user.home") + "\\DiGarfo\\imagens\\imagem_receita\\";
		@Autowired
		private ReceitaRepository receitaRepository;
		//GET todas as receitas
		@GetMapping
		public Iterable<Receita> getReceita(){
			return receitaRepository.findAll();
		}
		//get all por usuario
		@GetMapping("/allRecipesForUser/{email}")
		public List<Receita> getAllRecipesForUser(@PathVariable String email){
			return receitaRepository.pegarReceitasDoUsuario(email);
		}
		//get all receitas false aprovada
		@GetMapping("/aprovadaFalse")
		public List<Receita> getReceitasFalseAprovada(){
			return receitaRepository.findAllByAprovacaoFalse();
		}
		//get all receitas true aprovada
		@GetMapping("/aprovada")
		public List<Receita> getReceitasTrueAprovada(){
			return receitaRepository.findAllByAprovacaoTrue();
		}
		//get receita barra de pesquisas 
		@GetMapping("/search/{termo}")
		public List<Receita> search(@PathVariable String termo) {
			return receitaRepository.buscarPorNomeOuCategoriaAprovadas(termo);
		}
		//get receita pelo nome exato dela
		@GetMapping("/findbynome/{nome_receita}")
		public Receita getReceitaByNome(@PathVariable String nome_receita) {
			return receitaRepository.findReceitaByNome(nome_receita);
		}
		//GET por id	
		@GetMapping("/{id_receita}")
		public Receita buscaReceitaPorId(@PathVariable Long id_receita) {
			return receitaRepository.findById(id_receita).orElse(null);
		}
		@GetMapping("/receitaIDtrue/{id_receita}")
			public Receita pao(@PathVariable Long id_receita){
			return receitaRepository.pegarReceitaAprovID(id_receita);
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
		//criar receita com imagem
		@PostMapping(value = "/ReceitaWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public Receita adicionarReceitaIMG(@ModelAttribute Receita receita, @RequestParam("file") MultipartFile arquivo) {
			//primeiro salvando para depois colocar a imagem
			Receita receitaSalva = receitaRepository.save(receita);
			try {
				//verificacao para add imagem 
				if(arquivo != null) {
					byte[] bytes = arquivo.getBytes();
					Path caminho = Paths.get(caminhoimg + String.valueOf(receita.getId_receita() + arquivo.getOriginalFilename()));
					Files.write(caminho, bytes);
					receita.setImg_receita(String.valueOf(receita.getId_receita() + arquivo.getOriginalFilename()));
				} else {
					receita.setImg_receita(null);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
			return receitaRepository.save(receitaSalva);
		}
		//UPDATE receita por id
		@PutMapping("/{id_receita}")
		public Receita atualizaReceita(@PathVariable Long id_receita, @RequestBody Receita receita) {
			receita.setId_receita(id_receita);
			return receitaRepository.save(receita);
		}
		//DELETE receita por id
		@DeleteMapping("/{id_receita}")
		public ResponseEntity<String> deletaReceita(@PathVariable Long id_receita) {
		    try {
		        receitaRepository.deleteById(id_receita);
		        System.out.println("Receita deletada");
		        return ResponseEntity.ok("Receita deletada com sucesso"); // Retorna status 200 com mensagem
		    } catch (Exception e) {
		        System.out.println("Erro ao deletar receita: " + e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar receita"); // Retorna status 500 em caso de erro
		    }
		}

}

