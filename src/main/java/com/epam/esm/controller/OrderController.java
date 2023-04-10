package com.epam.esm.controller;

import com.epam.esm.dto.OrderRequestModel;
import com.epam.esm.dto.OrderResponseModel;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * To create order
     *
     * @param orderRequestModel order request model
     * @return status of operation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody OrderRequestModel orderRequestModel) {
        orderService.create(orderRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * To get order by id
     *
     * @param id order id
     * @return ResponseEntity with found order
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseModel> getOrderById(@PathVariable long id) {
        OrderResponseModel order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
