package com.workskop.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "services")
@Inheritance(strategy = InheritanceType.JOINED)
public class ServiceWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryService category;
    private String duration;


}
