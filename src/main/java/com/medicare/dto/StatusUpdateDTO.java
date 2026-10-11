package com.medicare.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Updating Appointment Status.
 */
public class StatusUpdateDTO {

    @NotBlank(message = "Status is required (BOOKED, CONFIRMED, CANCELLED, COMPLETED)")
    private String status;

    public StatusUpdateDTO() {
    }

    public StatusUpdateDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
