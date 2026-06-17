package com.suyash.resumeanalyzer.repository;

import com.suyash.resumeanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}