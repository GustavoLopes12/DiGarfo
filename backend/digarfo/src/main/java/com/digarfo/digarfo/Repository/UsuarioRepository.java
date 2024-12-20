package com.digarfo.digarfo.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	
	@Query("SELECT u FROM Usuario u WHERE u.nome_usuario = :nome_usuario")
	List<Usuario> findByNome_usuario(String nome_usuario);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
	Usuario findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
	
	//buscar usuario pela receita dele true
	@Query("SELECT r.usuario FROM Receita r WHERE r.id_receita = :idReceita AND r.aprovada = true")//coloquei o true
	Usuario findUsuarioByReceitaId(@Param("idReceita") Long idReceita);
	
	//buscar usuario pela receita dele falsa
		@Query("SELECT r.usuario FROM Receita r WHERE r.id_receita = :idReceita AND r.aprovada = false")//coloquei o true
		Usuario findUsuarioByReceitaIdF(@Param("idReceita") Long idReceita);
	
	
	
	

 
	
}
