package com.E_commerce.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name="invalid_token")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidToken extends BaseEntity{
    @Id
    private String id;
    private Date expiryTime;
}
