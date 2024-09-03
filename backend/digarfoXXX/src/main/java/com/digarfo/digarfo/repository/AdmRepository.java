package com.digarfo.digarfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digarfo.digarfo.model.Adm;

@Repository
public interface AdmRepository extends JpaRepository<Adm, Integer>{

}
