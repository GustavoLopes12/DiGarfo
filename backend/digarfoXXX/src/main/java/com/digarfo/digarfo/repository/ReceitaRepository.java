package com.digarfo.digarfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digarfo.digarfo.model.Receita;
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Integer>{
	@Query("SELECT r FROM receita r WHERE r.nome_receita LIKE %:nome_receita%")
	Iterable<Receita> findByNome(String nome_receita);
	
	@Query("SELECT r FROM receita r WHERE r.categoria LIKE %:categoria%")
	Iterable<Receita> findByCategoria(String categoria);
	
}
