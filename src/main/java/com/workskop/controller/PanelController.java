package com.workskop.controller;

import com.workskop.config.AuthenticationRequest;
import com.workskop.dto.UserDto;
import com.workskop.entity.ServiceWorker;
import com.workskop.entity.User;
import com.workskop.repository.ServiceRepository;
import com.workskop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
public class PanelController {


    private final ServiceRepository serviceRepository;

    public PanelController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/panel")
    public String getPanel(Model model) {
        List<ServiceWorker> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "panel";
    }

}
