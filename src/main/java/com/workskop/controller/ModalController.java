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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("modals")
public class ModalController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;


    @Autowired
    public ModalController(OrderRepository orderRepository, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }
    @GetMapping("edit-order-modal")
    public String editOrderModal(@RequestParam(value="id") Long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid order Id:" + id));
        List<Client> clients = clientRepository.findAll();
        List<ServiceWorker> services = serviceRepository.findAll();

        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        model.addAttribute("services", services);
        return "/modals/edit-order-modal";
    }


    @GetMapping("order-form")
    public String addOrderModal(Model model) {
        model.addAttribute("order", new Order());
        List<Client> clients = clientRepository.findAll();
        List<ServiceWorker> services = serviceRepository.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("services", services);
        return "/modals/order-form";
    }


    @PostMapping("/save")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Optional<Order> findOne (Long id) {
        return orderRepository.findById(id);
    }





}