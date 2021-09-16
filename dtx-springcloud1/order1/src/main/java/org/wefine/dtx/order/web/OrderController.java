package org.wefine.dtx.order.web;

import org.wefine.dtx.order.dao.OrderRepository;
import org.wefine.dtx.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wefine.dtx.service.IOrderService;
import org.wefine.dtx.service.dto.OrderDTO;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController implements IOrderService {

    @PostConstruct
    public void init() {
        Order order = new Order();
        order.setAmount(100);
        order.setTitle("MyOrder");
        order.setDetail("Bought a imooc course");
        orderRepository.save(order);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @PostMapping("")
    public OrderDTO create(@RequestBody OrderDTO dto) {
        Order order = new Order();
        order.setAmount(dto.getAmount());
        order.setTitle(dto.getTitle());
        order.setDetail(dto.getDetail());
        order = orderRepository.save(order);
        dto.setId(order.getId());
        return dto;
    }

    @Override
    @GetMapping("/{id}")
    public OrderDTO getMyOrder(@PathVariable Long id) {
        Order order = orderRepository.getOne(id);
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setAmount(order.getAmount());
        dto.setTitle(order.getTitle());
        dto.setDetail(order.getDetail());
        return dto;
    }

    @GetMapping("")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}
