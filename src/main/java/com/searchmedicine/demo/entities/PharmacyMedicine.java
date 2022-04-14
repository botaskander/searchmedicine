package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pharmacies_medicines")
public class PharmacyMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private double price;

    @ManyToOne
    private CompanyMedicine companyMedicine;

    @ManyToOne
    private Pharmacy pharmacy;

}
