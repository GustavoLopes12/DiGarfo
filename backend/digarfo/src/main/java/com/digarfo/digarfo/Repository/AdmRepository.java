package com.digarfo.digarfo.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Adm;
@Repository
public interface AdmRepository extends CrudRepository<Adm, String>{
	@Query("SELECT a FROM Adm a WHERE a.email = :email AND a.senha = :senha")
	Adm findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}

