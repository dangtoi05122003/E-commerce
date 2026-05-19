package com.E_commerce.Entity;

import java.util.Map;

import com.E_commerce.Enum.AttributeKey;
import com.E_commerce.Enum.VariantStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product_variant")
public class ProductVariantEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    @Enumerated(EnumType.STRING)
    private VariantStatus status;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @ElementCollection
    @CollectionTable(name = "variant_attributes", joinColumns = @JoinColumn(name = "variant_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    private Map<AttributeKey, String> attributes;
    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL)
    private InventoryEntity inventory;
}
