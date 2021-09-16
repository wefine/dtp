package org.wefine.dtx.chain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private Integer deposit;
}
