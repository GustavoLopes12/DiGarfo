package com.DiGarfo.DiGarfo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.DiGarfo.DiGarfo.Model.Adm;
@Repository
public interface AdmRepository extends CrudRepository<Adm, String>{

}

