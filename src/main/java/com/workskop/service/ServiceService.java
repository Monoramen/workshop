package com.workskop.service;


import com.workskop.entity.ServiceWorker;
import com.workskop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class ServiceService {
    private  ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceWorker createService(ServiceWorker service) {
        return serviceRepository.save(service);
    }
}