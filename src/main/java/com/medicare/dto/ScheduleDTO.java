package com.medicare.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Doctor Schedule.
 */
public class ScheduleDTO {

    private Long id;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    private String doctorName;

    @NotBlank(message = "Day is required")
    private String day;

    @NotBlank(message = "Start time is required")
    private String startTime;

    @NotBlank(message = "End time is required")
    private String endTime;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Long id, Long doctorId, String doctorName, String day, String startTime, String endTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
