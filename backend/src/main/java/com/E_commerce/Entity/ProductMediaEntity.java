package com.E_commerce.Entity;

import com.E_commerce.Enum.MediaStatus;
import com.E_commerce.Enum.MediaType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_media")
@Setter
@Getter
public class ProductMediaEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private Boolean isPrimary;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @Enumerated(EnumType.STRING)
    private MediaStatus status;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
}
