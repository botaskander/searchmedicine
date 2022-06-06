package com.searchmedicine.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pharmacy_requests")
public class RegisterRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name",columnDefinition = "TEXT")
    private String name;

    @Column(name = "email",columnDefinition = "TEXT")
    private String email;

    @Column(name = "password",columnDefinition = "TEXT")
    private String staticPassword;

    @Column(name = "work_start_time",columnDefinition = "time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time",columnDefinition = "time")
    private LocalTime workEndTime;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    @Column(name = "is_seen")
    private Boolean isSeen;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "full_name")
    private String fullName;

    @Column(name="register_date")
    private LocalDateTime registerDate;

    @OneToOne
    private Address address;



}
