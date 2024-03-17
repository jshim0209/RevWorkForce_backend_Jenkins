package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="holiday")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {

//    id serial primary key,
//    name varchar(64),
//    start_date date,
//    end_date date

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDate start_date;

    private LocalDate end_date;
}
