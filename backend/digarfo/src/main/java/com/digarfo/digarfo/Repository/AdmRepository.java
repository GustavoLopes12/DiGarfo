package com.digarfo.digarfo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.digarfo.digarfo.Model.Adm;
@Repository
public interface AdmRepository extends CrudRepository<Adm, String>{

}

