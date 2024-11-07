package com.digarfo.digarfo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
	
	// Para retornar todas as receitas reprovadas
	@Query("SELECT r FROM Receita r WHERE r.aprovada = false")
	List<Receita> findAllByAprovacaoFalse();
		
	//receita pelo nome exato dela
	@Query("SELECT r FROM Receita r WHERE r.nome_receita = :nome_receita")
	Receita findReceitaByNome(String nome_receita);
	
	// Para retornar todas as receitas aprovadas e com id especifico
	@Query("SELECT r FROM Receita r WHERE r.aprovada = true AND r.id_receita = :id_receita ")
	Receita pegarReceitaAprovID(Long id_receita);
		
	//todas as receitas por usuario
	@Query("SELECT r FROM Receita r WHERE r.usuario.email = :email")
	List<Receita> pegarReceitasDoUsuario(String email);
	
}
