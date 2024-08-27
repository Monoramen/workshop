package com.workskop.controller;



import com.workskop.entity.Client;
import com.workskop.entity.Order;
import com.workskop.entity.ServiceWorker;
import com.workskop.repository.ClientRepository;
import com.workskop.repository.OrderRepository;
import com.workskop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller

public class OrderController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;


    @Autowired
    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        Iterable<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders")
    public String saveOrder(@ModelAttribute("order") Order order,
                            @RequestParam("clientId") Long clientId,
                            @RequestParam("serviceId") Long serviceId) {
        order.setClient(clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id: " + clientId)));
        order.setService(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service Id: " + serviceId)));
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/new")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("services", serviceRepository.findAll());
        return "modals/order-form";
    }

    @GetMapping("/orders/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid order Id:" + id));
        List<Client> clients = clientRepository.findAll();
        List<ServiceWorker> services = serviceRepository.findAll();

        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        model.addAttribute("services", services);

        return "edit-order";
    }


//    @PostMapping("/order/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
//                                             @ModelAttribute("order") Order order,
//                                             @RequestParam("clientId") Long clientId,
//                                             @RequestParam("serviceId") Long serviceId,
//                                             @RequestParam("date") LocalDate date) {
//        Order existingOrder = orderRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("Invalid order Id:" + id));
//
//        Client client = clientRepository.findById(clientId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id: " + clientId));
//        ServiceWorker service = serviceRepository.findById(serviceId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid service Id: " + serviceId));
//
//        existingOrder.setClient(client);
//        existingOrder.setService(service);
//        existingOrder.setDate(date);
//
//        Order updatedOrder = orderRepository.save(existingOrder);
//
//        return ResponseEntity.ok(updatedOrder);
//    }




}
