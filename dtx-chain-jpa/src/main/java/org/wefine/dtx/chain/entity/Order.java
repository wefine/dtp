package org.wefine.dtx.chain.entity;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long customerId;
    private String title;
    private Integer amount;
}
