package org.wefine.dtx.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.wefine.dtx.service.IOrderService;
import org.wefine.dtx.service.dto.OrderDTO;

@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends IOrderService {

    @GetMapping("/{id}")
    OrderDTO getMyOrder(@PathVariable(name = "id") Long id);

    @Override
    @PostMapping("")
    OrderDTO create(@RequestBody OrderDTO dto);
}
