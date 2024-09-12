package com.DiGarfo.DiGarfo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.DiGarfo.DiGarfo.Model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	
	/*@Query("SELECT u FROM usuario u WHERE u.nome_usuario = :nome_usuario")
	Iterable<Usuario> findByNome_usuario(String nome_usuario);*/
	
}
