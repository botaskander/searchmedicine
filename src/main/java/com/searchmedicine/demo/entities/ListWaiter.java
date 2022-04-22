package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "list_waiter")
public class ListWaiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "is_appear")
    private Boolean isAppear;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private Users users;
}
