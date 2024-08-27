package com.workskop.dto;

import java.time.LocalDate;

public class OrderUpdateDTO {
    private Long clientId;
    private Long serviceId;
    private LocalDate date;

    // Геттеры и сеттеры
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

