package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

	@Query("SELECT u FROM usuario u WHERE u.nome_usuario LIKE %:nome_usuario%")
	Iterable<Usuario> findByNome(String nome_usuario);

}
