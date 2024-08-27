package com.workskop.controller;

import com.workskop.dto.OrderUpdateDTO;
import com.workskop.entity.Client;
import com.workskop.entity.Order;
import com.workskop.entity.ServiceWorker;
import com.workskop.repository.ClientRepository;
import com.workskop.repository.OrderRepository;
import com.workskop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class ModalRestController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public ModalRestController(OrderRepository orderRepository, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/getorder/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
                                             @ModelAttribute("order") Order order,
                                             @RequestParam("clientId") Long clientId,
                                             @RequestParam("serviceId") Long serviceId,
                                             @RequestParam("date") LocalDate date) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid order Id:" + id));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id: " + clientId));
        ServiceWorker service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service Id: " + serviceId));

        existingOrder.setClient(client);
        existingOrder.setService(service);
        existingOrder.setDate(date);

        Order updatedOrder = orderRepository.save(existingOrder);

        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        Order savedOrder = orderRepository.save(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrder.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/order/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable(value = "id") Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
