package com.medicare.service;

import com.medicare.dto.AdminDTO;
import com.medicare.dto.LoginRequestDTO;
import com.medicare.entity.Admin;
import com.medicare.exception.BadRequestException;
import com.medicare.exception.ResourceNotFoundException;
import com.medicare.repository.AdminRepository;
import com.medicare.repository.AppointmentRepository;
import com.medicare.repository.DoctorRepository;
import com.medicare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation for Admin Authentication and Dashboard statistics.
 */
@Service
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        DoctorRepository doctorRepository,
                        PatientRepository patientRepository,
                        AppointmentRepository appointmentRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AdminDTO loginAdmin(LoginRequestDTO req) {
        Admin admin = adminRepository.findByUsername(req.getEmail())
                .or(() -> adminRepository.findByEmail(req.getEmail()))
                .orElseThrow(() -> new ResourceNotFoundException("Invalid admin username/email or password."));

        if (!admin.getPassword().equals(req.getPassword())) {
            throw new BadRequestException("Invalid admin username/email or password.");
        }

        AdminDTO dto = new AdminDTO(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getRole());
        dto.setStatistics(getDashboardStats());
        return dto;
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalDoctors = doctorRepository.count();
        long totalPatients = patientRepository.count();
        long totalAppointments = appointmentRepository.count();

        stats.put("totalDoctors", totalDoctors);
        stats.put("totalPatients", totalPatients);
        stats.put("totalAppointments", totalAppointments);

        return stats;
    }
}
