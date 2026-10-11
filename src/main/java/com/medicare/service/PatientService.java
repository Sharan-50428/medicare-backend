package com.medicare.service;

import com.medicare.dto.LoginRequestDTO;
import com.medicare.dto.PatientDTO;
import com.medicare.entity.Patient;
import com.medicare.exception.BadRequestException;
import com.medicare.exception.ResourceNotFoundException;
import com.medicare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Patient operations.
 */
@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientDTO registerPatient(PatientDTO dto) {
        if (patientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("An account with email " + dto.getEmail() + " already exists.");
        }

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        patient.setPassword(dto.getPassword());

        Patient saved = patientRepository.save(patient);
        return mapToDTO(saved);
    }

    public PatientDTO loginPatient(LoginRequestDTO loginReq) {
        Patient patient = patientRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password."));

        if (!patient.getPassword().equals(loginReq.getPassword())) {
            throw new BadRequestException("Invalid email or password.");
        }

        return mapToDTO(patient);
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToDTO(patient);
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO updatePatient(Long id, PatientDTO dto) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        if (!existing.getEmail().equalsIgnoreCase(dto.getEmail()) && patientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email " + dto.getEmail() + " is already in use by another patient.");
        }

        existing.setName(dto.getName());
        existing.setAge(dto.getAge());
        existing.setGender(dto.getGender());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        existing.setAddress(dto.getAddress());
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            existing.setPassword(dto.getPassword());
        }

        Patient updated = patientRepository.save(existing);
        return mapToDTO(updated);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    private PatientDTO mapToDTO(Patient p) {
        PatientDTO dto = new PatientDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setAge(p.getAge());
        dto.setGender(p.getGender());
        dto.setPhone(p.getPhone());
        dto.setEmail(p.getEmail());
        dto.setAddress(p.getAddress());
        return dto;
    }
}
