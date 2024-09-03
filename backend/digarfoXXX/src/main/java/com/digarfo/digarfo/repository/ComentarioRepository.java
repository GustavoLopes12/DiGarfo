package com.digarfo.digarfo.repository;

import org.springframework.stereotype.Repository;

import com.digarfo.digarfo.model.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

}
