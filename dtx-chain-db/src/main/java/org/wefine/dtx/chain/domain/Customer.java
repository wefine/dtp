package org.wefine.dtx.chain.domain;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String username;
    private Integer deposit;
}
