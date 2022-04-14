package com.searchmedicine.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicines")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name",columnDefinition="TEXT")
    private String name;

    @Column(name = "description",columnDefinition="TEXT")
    private String description;

    @Column(name = "is_pres_only")
    private Boolean isPresOnly;

    @Column(name = "is_exchange")
    private Boolean isExchange;

    @Column(name = "storage_condition",columnDefinition="TEXT")
    private String storageCondition;

    @Column(name = "instructions",columnDefinition="TEXT")
    private String instructions;

    @Column(name = "indications", columnDefinition="TEXT")
    private String indications;

    @ManyToOne
    private FarmGroup farmGroup;

    @ManyToOne
    private  Company company;




}
