package com.medicare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Doctor Entity
 * Maps to the 'doctors' table in MySQL.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Specialization is required")
    @Column(nullable = false)
    private String specialization;

    @NotBlank(message = "Qualification is required")
    @Column(nullable = false)
    private String qualification;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Column(nullable = false)
    private Integer experience;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a valid 10-digit number")
    @Column(nullable = false, length = 15)
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Consultation fee is required")
    @Min(value = 0, message = "Consultation fee cannot be negative")
    @Column(nullable = false)
    private Double consultationFee;

    @Column(nullable = false)
    private String status = "AVAILABLE"; // AVAILABLE, ON_LEAVE, BUSY

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorSchedule> schedules = new ArrayList<>();

    // Default Constructor
    public Doctor() {
    }

    // Parameterized Constructor
    public Doctor(Long id, String name, String specialization, String qualification, Integer experience, 
                  String phone, String email, Double consultationFee, String status) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experience = experience;
        this.phone = phone;
        this.email = email;
        this.consultationFee = consultationFee;
        this.status = status != null ? status : "AVAILABLE";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<DoctorSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<DoctorSchedule> schedules) {
        this.schedules = schedules;
    }
}
