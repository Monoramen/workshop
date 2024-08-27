package com.workskop.repository;


import com.workskop.entity.ServiceWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceWorker, Long> {
    ServiceWorker findByName(String name);
    List<ServiceWorker> findByCategoryName(String categoryName);
    List<ServiceWorker> findAllByOrderByCategoryAsc();
}

