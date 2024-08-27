package com.workskop.controller;



import com.workskop.entity.CategoryService;
import com.workskop.entity.ServiceWorker;
import com.workskop.repository.CategoryRepository;
import com.workskop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ServiceController {
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;



    @Autowired
    public ServiceController(ServiceRepository serviceRepository, CategoryRepository categoryRepository) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;

    }
    @GetMapping("/services")
    public String getServices(@RequestParam(required = false) String sort, Model model) {
        List<ServiceWorker> services;
        if (sort != null && sort.equals("category")) {
            services = serviceRepository.findAllByOrderByCategoryAsc();
        } else {
            services = serviceRepository.findAll();
        }
        model.addAttribute("services", services);
        return "services";
    }


    @GetMapping("/services/{id}/edit")
    public String editService(@PathVariable Long id, Model model) {
        ServiceWorker service = serviceRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid service Id:" + id));
        List<CategoryService> categories = categoryRepository.findAll();
        model.addAttribute("service", service);
        model.addAttribute("categories", categories);
        return "edit-service";
    }


    @PostMapping("/services/{id}")
    public String saveService(@PathVariable Long id, @ModelAttribute("service") ServiceWorker service, @RequestParam("categoryId") Long categoryId, Model model) {
        CategoryService category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new IllegalArgumentException("Invalid category Id:" + categoryId));
        service.setCategory(category);
        serviceRepository.save(service);
        return "redirect:/services";
    }




}
