package com.E_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_commerce.Entity.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String>{
    
}
