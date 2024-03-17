package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;

@Entity
public class Leave {

//    id serial primary key,
//    account_id int,
//    type varchar(64),
//    hours int,
//    foreign key (account_id) references account(id)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int accountId;
    private String type;
    private int hours;

    public int getAccountId(){
        return accountId;
    }

    public String getType() {
        return type;
    }

    public int getHours() {
        return hours;
    }
}
