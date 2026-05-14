package com.E_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.UserAddress;

import io.lettuce.core.dynamic.annotation.Param;
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
    @Modifying
    @Query(value = "SELECT * FROM user_address WHERE user_id = :userId AND is_deleted = false", nativeQuery = true)
    List<UserAddress> findAllByUserIdAndIsDeletedFalse(@Param("userId") Long userId);
    @Modifying
    @Query("UPDATE UserAddress ua SET ua.isDefault = false WHERE ua.user.id = :userId AND ua.isDeleted = false")
    void clearDefaultByUserId(@Param("userId") Long userId);
}
