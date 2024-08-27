package com.workskop.repository;

import com.workskop.entity.Client;
import com.workskop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String email);
    Client findByEmailIgnoreCase(String email);
}
