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
@Table(name = "list_reserver")
public class ListReserver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "is_took")
    private Boolean isTook;

    @Column(name = "is_expired")
    private Boolean  isExpired;

    @Column(name = "until_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime untilTime;

    @Column(name = "reserved_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime reservedTime;

    @Column(name = "count")
    private int count;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private Pharmacy pharmacy;

    @ManyToOne
    private Users users;

}
