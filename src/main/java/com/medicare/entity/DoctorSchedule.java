package com.medicare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * DoctorSchedule Entity
 * Maps to the 'doctor_schedules' table in MySQL.
 */
@Entity
@Table(name = "doctor_schedules")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotBlank(message = "Day is required (e.g., Monday, Tuesday)")
    @Column(nullable = false)
    private String day;

    @NotBlank(message = "Start time is required (e.g., 09:00 AM or 10:00)")
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotBlank(message = "End time is required (e.g., 01:00 PM or 17:00)")
    @Column(name = "end_time", nullable = false)
    private String endTime;

    // Default Constructor
    public DoctorSchedule() {
    }

    // Parameterized Constructor
    public DoctorSchedule(Long id, Doctor doctor, String day, String startTime, String endTime) {
        this.id = id;
        this.doctor = doctor;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
