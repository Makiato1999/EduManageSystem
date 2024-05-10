package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Easyclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EasyClassRepository extends JpaRepository<Easyclass, Integer> {

}
