package com.digarfo.digarfo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	/*
	@Query("SELECT u FROM usuario u WHERE u.nome_usuario LIKE %:nome_usuario%")
	Iterable<Usuario> findByNome(String nome_usuario);
	*/
}
