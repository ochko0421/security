package com.example.demo.Students;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface studentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();

    Student findById(int id);
}
