package com.medicare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Appointment Entity
 * Maps to the 'appointments' table in MySQL.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull(message = "Appointment date is required")
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @NotBlank(message = "Appointment time is required")
    @Column(name = "appointment_time", nullable = false)
    private String appointmentTime;

    @NotBlank(message = "Reason for visit is required")
    @Column(nullable = false, length = 500)
    private String reason;

    @NotBlank(message = "Appointment status is required")
    @Column(nullable = false)
    private String status = "BOOKED"; // BOOKED, CONFIRMED, CANCELLED, COMPLETED

    // Default Constructor
    public Appointment() {
    }

    // Parameterized Constructor
    public Appointment(Long id, Patient patient, Doctor doctor, LocalDate appointmentDate, 
                       String appointmentTime, String reason, String status) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status != null ? status : "BOOKED";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
