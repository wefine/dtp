package org.wefine.dtx.service;

import org.wefine.dtx.service.dto.OrderDTO;

public interface IOrderService {
    OrderDTO create(OrderDTO dto);
    OrderDTO getMyOrder(Long id);
}
