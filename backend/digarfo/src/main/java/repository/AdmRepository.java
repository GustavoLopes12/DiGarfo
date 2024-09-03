package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Adm;

@Repository
public interface AdmRepository extends JpaRepository<Adm, Integer>{

}
