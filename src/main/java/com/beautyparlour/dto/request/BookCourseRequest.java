package com.beautyparlour.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BookCourseRequest {
    @NotNull(message = "Course ID is required")
    private UUID courseId;

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    // Constructors
    public BookCourseRequest() {}

    // Getters and Setters
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
