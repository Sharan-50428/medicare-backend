package com.medicare.controller;

import com.medicare.dto.ApiResponseDTO;
import com.medicare.dto.LoginRequestDTO;
import com.medicare.dto.PatientDTO;
import com.medicare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Patient endpoints.
 * Base URL: /api/patients
 */
@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<PatientDTO>> registerPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO created = patientService.registerPatient(patientDTO);
        return new ResponseEntity<>(
                ApiResponseDTO.success("Patient registered successfully", created),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<PatientDTO>> loginPatient(@Valid @RequestBody LoginRequestDTO loginRequest) {
        PatientDTO patient = patientService.loginPatient(loginRequest);
        return ResponseEntity.ok(ApiResponseDTO.success("Login successful", patient));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PatientDTO>>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponseDTO.success("Patients retrieved successfully", patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PatientDTO>> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Patient details retrieved", patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PatientDTO>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updated = patientService.updatePatient(id, patientDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Patient updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Patient deleted successfully", null));
    }
}
