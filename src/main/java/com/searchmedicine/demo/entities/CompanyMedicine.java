package com.searchmedicine.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "companies_medicines")
public class CompanyMedicine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="id")
  private Long id;

  @ManyToOne
  private Medicine medicine;

  @ManyToOne
  private Company company;

  @Column(name = "url",columnDefinition = "TEXT")
  private  String  url;

  @Column(name = "is_exchange")
  private Boolean isExchange;


}
