package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users_medicines")
public class UserMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private double price;

    @Column(name = "comment",columnDefinition = "TEXT")
    private String comment;

    @Column(name = "exp_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime expDate;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private Users user;
}
