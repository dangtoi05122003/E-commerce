package com.E_commerce.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Enum.StatusUser;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByStatus(StatusUser status);
}
