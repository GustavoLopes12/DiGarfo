package com.DiGarfo.DiGarfo.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.DiGarfo.DiGarfo.Model.Comentario;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long>{

}
