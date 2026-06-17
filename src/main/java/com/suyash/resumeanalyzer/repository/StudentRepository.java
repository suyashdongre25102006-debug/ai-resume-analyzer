package com.suyash.resumeanalyzer.repository;

import com.suyash.resumeanalyzer.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long>
{

}