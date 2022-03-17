package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
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


    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "exp_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime expDate;

    @Column(name = "url_image", columnDefinition = "TEXT")
    private String urlImage;

    @Column(name = "addedDate")
    private Date addedDate;

    @ManyToOne
    private CompanyMedicine companyMedicine;

    @Column(name = "phone",columnDefinition = "TEXT")
    private String phone;

    @ManyToOne
    private Users user;
}
