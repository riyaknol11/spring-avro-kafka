package com.knoldus.controller;

import com.knoldus.model.CoffeeOrder;
import com.knoldus.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/avro/api")
@Slf4j
public class CoffeeOrderController {

    private CoffeeOrderService coffeeOrderService;

    public CoffeeOrderController(CoffeeOrderService coffeeOrderService) {
        this.coffeeOrderService = coffeeOrderService;
    }

    @PostMapping("/coffee_order")
    @ResponseStatus(HttpStatus.CREATED)
    public void newOrder(@RequestBody CoffeeOrder coffeeOrder) throws IOException {
        log.info("Received the order : {}", coffeeOrder);
        coffeeOrderService.processOrder(coffeeOrder);

    }
}
