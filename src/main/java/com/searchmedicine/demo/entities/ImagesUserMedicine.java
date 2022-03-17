package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images_users_medicines")
public class ImagesUserMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "url",columnDefinition = "TEXT")
    private  String  url;

    @Column(name = "addedDate")
    private Date addedDate;

    @ManyToOne
    private UserMedicine userMedicine;
}
