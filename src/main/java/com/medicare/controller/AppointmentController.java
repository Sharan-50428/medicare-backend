package com.medicare.controller;

import com.medicare.dto.ApiResponseDTO;
import com.medicare.dto.AppointmentDTO;
import com.medicare.dto.StatusUpdateDTO;
import com.medicare.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Appointment endpoints.
 * Base URL: /api/appointments
 */
@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AppointmentDTO>> bookAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO booked = appointmentService.bookAppointment(appointmentDTO);
        return new ResponseEntity<>(
                ApiResponseDTO.success("Appointment booked successfully!", booked),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AppointmentDTO>>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(ApiResponseDTO.success("Appointments retrieved successfully", appointments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AppointmentDTO>> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Appointment retrieved", appointment));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponseDTO<List<AppointmentDTO>>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(ApiResponseDTO.success("Patient appointments retrieved", appointments));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponseDTO<List<AppointmentDTO>>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(ApiResponseDTO.success("Doctor appointments retrieved", appointments));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO<AppointmentDTO>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateDTO statusUpdate) {
        AppointmentDTO updated = appointmentService.updateAppointmentStatus(id, statusUpdate.getStatus());
        return ResponseEntity.ok(ApiResponseDTO.success("Appointment status updated to " + statusUpdate.getStatus(), updated));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDTO<Void>> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Appointment cancelled successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Appointment record deleted successfully", null));
    }
}
