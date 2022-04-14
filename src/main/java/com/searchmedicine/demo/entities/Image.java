package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images_medicine")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "url",columnDefinition = "TEXT")
    private  String  url;

    @Column(name = "addedDate")
    private Date addedDate;

    @ManyToOne
    private Medicine medicine;


}
