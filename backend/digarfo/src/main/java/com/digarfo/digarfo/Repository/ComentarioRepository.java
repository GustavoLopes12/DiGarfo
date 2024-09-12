package com.digarfo.digarfo.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Comentario;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long>{

}
