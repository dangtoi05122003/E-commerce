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
@Table(name="order_item")
@Getter
@Setter
public class OrderItemEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="orders_id", nullable=false)
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariantEntity productVariant;
    private int quantity;
    private Double price;
}
