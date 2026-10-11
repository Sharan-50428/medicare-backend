package com.medicare.service;

import com.medicare.dto.DoctorDTO;
import com.medicare.dto.ScheduleDTO;
import com.medicare.entity.Doctor;
import com.medicare.exception.BadRequestException;
import com.medicare.exception.ResourceNotFoundException;
import com.medicare.repository.DoctorRepository;
import com.medicare.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Doctor operations.
 */
@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToDTOWithSchedules)
                .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return mapToDTOWithSchedules(doctor);
    }

    public DoctorDTO createDoctor(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setQualification(dto.getQualification());
        doctor.setExperience(dto.getExperience());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");

        Doctor saved = doctorRepository.save(doctor);
        return mapToDTO(saved);
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO dto) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        existing.setName(dto.getName());
        existing.setSpecialization(dto.getSpecialization());
        existing.setQualification(dto.getQualification());
        existing.setExperience(dto.getExperience());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        existing.setConsultationFee(dto.getConsultationFee());
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        Doctor updated = doctorRepository.save(existing);
        return mapToDTOWithSchedules(updated);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    public List<DoctorDTO> searchDoctors(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllDoctors();
        }
        return doctorRepository.searchByNameOrSpecialization(query.trim()).stream()
                .map(this::mapToDTOWithSchedules)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> filterDoctors(String name, String specialization) {
        String cleanName = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        String cleanSpec = (specialization != null && !specialization.trim().isEmpty() && !specialization.equalsIgnoreCase("ALL")) ? specialization.trim() : null;

        return doctorRepository.filterDoctors(cleanName, cleanSpec).stream()
                .map(this::mapToDTOWithSchedules)
                .collect(Collectors.toList());
    }

    public DoctorDTO mapToDTO(Doctor doc) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doc.getId());
        dto.setName(doc.getName());
        dto.setSpecialization(doc.getSpecialization());
        dto.setQualification(doc.getQualification());
        dto.setExperience(doc.getExperience());
        dto.setPhone(doc.getPhone());
        dto.setEmail(doc.getEmail());
        dto.setConsultationFee(doc.getConsultationFee());
        dto.setStatus(doc.getStatus());
        return dto;
    }

    public DoctorDTO mapToDTOWithSchedules(Doctor doc) {
        DoctorDTO dto = mapToDTO(doc);
        List<ScheduleDTO> schedules = scheduleRepository.findByDoctorId(doc.getId()).stream()
                .map(s -> new ScheduleDTO(s.getId(), doc.getId(), doc.getName(), s.getDay(), s.getStartTime(), s.getEndTime()))
                .collect(Collectors.toList());
        dto.setSchedules(schedules);
        return dto;
    }
}
