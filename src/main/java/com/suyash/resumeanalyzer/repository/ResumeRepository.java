package com.suyash.resumeanalyzer.repository;

import com.suyash.resumeanalyzer.model.Resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository
        extends JpaRepository<Resume, Long>
{
}