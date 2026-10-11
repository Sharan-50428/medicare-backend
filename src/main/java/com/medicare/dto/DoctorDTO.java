package com.medicare.dto;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Data Transfer Object for Doctor Details and Registration.
 */
public class DoctorDTO {

    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be a valid 10-digit number")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Consultation fee is required")
    @Min(value = 0, message = "Consultation fee cannot be negative")
    private Double consultationFee;

    private String status = "AVAILABLE";

    private List<ScheduleDTO> schedules;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String name, String specialization, String qualification, Integer experience, 
                     String phone, String email, Double consultationFee, String status) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experience = experience;
        this.phone = phone;
        this.email = email;
        this.consultationFee = consultationFee;
        this.status = status;
    }

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

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
