package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRespository extends JpaRepository<Courses, Integer> {
    List<Courses> findByOrderByNameDesc();

    List<Courses> findByOrderByName();

}
