package com.medicare.controller;

import com.medicare.dto.ApiResponseDTO;
import com.medicare.dto.DoctorDTO;
import com.medicare.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Doctor endpoints.
 * Base URL: /api/doctors
 */
@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<DoctorDTO>>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(ApiResponseDTO.success("Doctors retrieved successfully", doctors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<DoctorDTO>> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Doctor details retrieved", doctor));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<DoctorDTO>> addDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO created = doctorService.createDoctor(doctorDTO);
        return new ResponseEntity<>(
                ApiResponseDTO.success("Doctor added successfully", created),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<DoctorDTO>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updated = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Doctor updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Doctor deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<DoctorDTO>>> searchDoctors(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization) {

        List<DoctorDTO> result;
        if (query != null && !query.trim().isEmpty()) {
            result = doctorService.searchDoctors(query);
        } else {
            result = doctorService.filterDoctors(name, specialization);
        }
        return ResponseEntity.ok(ApiResponseDTO.success("Doctors search completed", result));
    }
}
