package com.workskop.controller;

import com.workskop.entity.Client;
import com.workskop.repository.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        List<Client> clients;
        if (clientRepository.findAll().isEmpty()) {
            clients = List.of();
        } else {
            clients = clientRepository.findAll();
        }
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/clients/new")
    public String showClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "modals/client-form";
    }

}
