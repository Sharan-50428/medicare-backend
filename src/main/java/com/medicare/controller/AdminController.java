package com.medicare.controller;

import com.medicare.dto.*;
import com.medicare.service.AdminService;
import com.medicare.service.AppointmentService;
import com.medicare.service.DoctorService;
import com.medicare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Admin endpoints.
 * Base URL: /api/admin
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public AdminController(AdminService adminService,
                           DoctorService doctorService,
                           PatientService patientService,
                           AppointmentService appointmentService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AdminDTO>> loginAdmin(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AdminDTO admin = adminService.loginAdmin(loginRequest);
        return ResponseEntity.ok(ApiResponseDTO.success("Admin login successful", admin));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = adminService.getDashboardStats();
        return ResponseEntity.ok(ApiResponseDTO.success("System statistics retrieved", stats));
    }

    @GetMapping("/doctors")
    public ResponseEntity<ApiResponseDTO<List<DoctorDTO>>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(ApiResponseDTO.success("All doctors retrieved", doctors));
    }

    @GetMapping("/patients")
    public ResponseEntity<ApiResponseDTO<List<PatientDTO>>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponseDTO.success("All patients retrieved", patients));
    }

    @GetMapping("/appointments")
    public ResponseEntity<ApiResponseDTO<List<AppointmentDTO>>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(ApiResponseDTO.success("All appointments retrieved", appointments));
    }
}
