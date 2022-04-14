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
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name",columnDefinition = "TEXT")
    private String name;

    @Column(name = "work_start_time",columnDefinition = "time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time",columnDefinition = "time")
    private LocalTime workEndTime;

    @Column(name = "is_work")
    private Boolean isWork;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    @Column(name = "off_phone")
    private String offPhone;

    @ManyToOne
    private Address address;
}
