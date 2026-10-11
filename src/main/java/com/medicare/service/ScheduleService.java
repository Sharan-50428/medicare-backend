package com.medicare.service;

import com.medicare.dto.ScheduleDTO;
import com.medicare.entity.Doctor;
import com.medicare.entity.DoctorSchedule;
import com.medicare.exception.ResourceNotFoundException;
import com.medicare.repository.DoctorRepository;
import com.medicare.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Doctor Schedules.
 */
@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<ScheduleDTO> getSchedulesByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        return scheduleRepository.findByDoctorId(doctorId).stream()
                .map(s -> new ScheduleDTO(s.getId(), doctor.getId(), doctor.getName(), s.getDay(), s.getStartTime(), s.getEndTime()))
                .collect(Collectors.toList());
    }

    public ScheduleDTO addSchedule(ScheduleDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDay(dto.getDay());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());

        DoctorSchedule saved = scheduleRepository.save(schedule);
        return new ScheduleDTO(saved.getId(), doctor.getId(), doctor.getName(), saved.getDay(), saved.getStartTime(), saved.getEndTime());
    }

    public ScheduleDTO updateSchedule(Long id, ScheduleDTO dto) {
        DoctorSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        existing.setDay(dto.getDay());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());

        DoctorSchedule updated = scheduleRepository.save(existing);
        return new ScheduleDTO(updated.getId(), updated.getDoctor().getId(), updated.getDoctor().getName(), updated.getDay(), updated.getStartTime(), updated.getEndTime());
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }
}
