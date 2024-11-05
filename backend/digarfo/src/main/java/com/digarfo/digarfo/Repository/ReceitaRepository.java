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
	
	//pegar usuario pela categoria e pelo nome
	
	
}
