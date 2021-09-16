package org.wefine.dtx.service.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String title;
    private String detail;
    private int amount;
}
