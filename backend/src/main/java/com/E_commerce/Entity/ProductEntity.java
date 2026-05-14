package com.E_commerce.Entity;

import java.util.ArrayList;
import java.util.List;

import com.E_commerce.Enum.ProductStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "product")
@Setter
@Getter
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMediaEntity> media = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
