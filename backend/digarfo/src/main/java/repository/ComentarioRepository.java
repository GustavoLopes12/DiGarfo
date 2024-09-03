package repository;

import org.springframework.stereotype.Repository;

import model.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

}
