package com.beautyparlour.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BookServiceRequest {
    @NotNull(message = "Service ID is required")
    private UUID serviceId;

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    // Constructors
    public BookServiceRequest() {}

    // Getters and Setters
    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
