package com.digarfo.digarfo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Receita;
@Repository
public interface ReceitaRepository extends CrudRepository<Receita, Long>{
	
	@Query("SELECT r FROM Receita r WHERE LOWER(r.nome_receita) LIKE LOWER(CONCAT('%', :nome_receita, '%'))")
	List<Receita> findByNome(String nome_receita);
	
	@Query("SELECT r FROM Receita r WHERE r.categoria LIKE %:categoria%")
	List<Receita> findByCategoria(String categoria);
	
	//pegar receita pela categoria e pelo nome
	@Query("SELECT r FROM Receita r WHERE (LOWER(r.nome_receita) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(r.categoria) LIKE LOWER(CONCAT('%', :termo, '%'))) AND r.aprovada = true")
	List<Receita> buscarPorNomeOuCategoriaAprovadas(String termo);
	
	// Para retornar todas as receitas aprovadas
	@Query("SELECT r FROM Receita r WHERE r.aprovada = true")
	List<Receita> findAllByAprovacaoTrue();
	
	// Para retornar todas as receitas aprovadas
		@Query("SELECT r FROM Receita r WHERE r.aprovada = false")
		List<Receita> findAllByAprovacaoFalse();
	
}
