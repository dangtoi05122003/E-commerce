package com.E_commerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_address")
@Getter
@Setter
public class UserAddress extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
    private String phone;
    private String addressLine;
    private String city;
    private String district;
    private String ward;
    private Boolean isDefault;
    private Boolean isDeleted;
}
