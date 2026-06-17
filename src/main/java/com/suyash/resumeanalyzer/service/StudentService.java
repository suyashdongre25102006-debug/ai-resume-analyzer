package com.suyash.resumeanalyzer.service;

import com.suyash.resumeanalyzer.exception.StudentNotFoundException;
import com.suyash.resumeanalyzer.model.Student;
import com.suyash.resumeanalyzer.repository.StudentRepository;
import com.suyash.resumeanalyzer.repository.ResumeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
    private final StudentRepository repository;

    public StudentService(StudentRepository repository)
    {
        this.repository = repository;
    }

    public Student saveStudent(Student student)
    {
        return repository.save(student);
    }
    public List<Student> getAllStudents()
    {
        return repository.findAll();
    }
    public void deleteStudent(Long id)
    {
        repository.deleteById(id);
    }
    public Student updateStudent(
            Long id,
            Student updatedStudent)
    {
        Student student =
                repository.findById(id)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student not found 😭"));

        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());

        return repository.save(student);
    }
}