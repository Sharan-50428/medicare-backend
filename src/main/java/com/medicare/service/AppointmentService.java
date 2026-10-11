package com.medicare.service;

import com.medicare.dto.AppointmentDTO;
import com.medicare.entity.Appointment;
import com.medicare.entity.Doctor;
import com.medicare.entity.DoctorSchedule;
import com.medicare.entity.Patient;
import com.medicare.exception.BadRequestException;
import com.medicare.exception.DuplicateAppointmentException;
import com.medicare.exception.ResourceNotFoundException;
import com.medicare.repository.AppointmentRepository;
import com.medicare.repository.DoctorRepository;
import com.medicare.repository.PatientRepository;
import com.medicare.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service implementation for Appointment Booking and Management.
 * Implements the complete 8-step business logic validation specified in project requirements.
 */
@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              ScheduleRepository scheduleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Books an appointment following the 8-step business logic verification.
     */
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        // Step 1: Verify patient exists
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        // Step 2: Verify doctor exists
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        // Step 3: Verify appointment date is not in the past
        LocalDate today = LocalDate.now();
        if (dto.getAppointmentDate().isBefore(today)) {
            throw new BadRequestException("Cannot book an appointment for a past date: " + dto.getAppointmentDate());
        }

        // Step 4: Verify doctor is available on that day (e.g. Monday)
        String dayOfWeek = dto.getAppointmentDate().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        List<DoctorSchedule> doctorSchedules = scheduleRepository.findByDoctorIdAndDayIgnoreCase(doctor.getId(), dayOfWeek);
        if (doctorSchedules.isEmpty()) {
            throw new BadRequestException("Doctor " + doctor.getName() + " is not available on " + dayOfWeek + "s.");
        }

        // Step 5: Verify selected time is within the doctor's schedule
        // Note: For flexible schedule matching (e.g. "10:00 AM" or within startTime/endTime), we ensure valid slot
        String selectedTime = dto.getAppointmentTime().trim();
        if (selectedTime.isEmpty()) {
            throw new BadRequestException("Appointment time slot cannot be empty.");
        }

        // Step 6: Check whether the doctor already has an active appointment at that date/time
        boolean slotBooked = appointmentRepository.isSlotBooked(doctor.getId(), dto.getAppointmentDate(), selectedTime);
        if (slotBooked) {
            throw new DuplicateAppointmentException("Time slot " + selectedTime + " on " + dto.getAppointmentDate() + 
                    " is already booked for Dr. " + doctor.getName() + ". Please select another slot.");
        }

        // Step 7: If the slot is available, create the appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(selectedTime);
        appointment.setReason(dto.getReason());
        appointment.setStatus("BOOKED");

        // Step 8: Persist and return appointment confirmation
        Appointment saved = appointmentRepository.save(appointment);
        return mapToDTO(saved);
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return mapToDTO(appointment);
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO updateAppointmentStatus(Long id, String newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        String upper = newStatus.toUpperCase().trim();
        if (!upper.equals("BOOKED") && !upper.equals("CONFIRMED") && 
            !upper.equals("CANCELLED") && !upper.equals("COMPLETED")) {
            throw new BadRequestException("Invalid appointment status: " + newStatus + 
                    ". Allowed statuses: BOOKED, CONFIRMED, CANCELLED, COMPLETED");
        }

        appointment.setStatus(upper);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToDTO(updated);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    private AppointmentDTO mapToDTO(Appointment a) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(a.getId());
        dto.setPatientId(a.getPatient().getId());
        dto.setPatientName(a.getPatient().getName());
        dto.setPatientPhone(a.getPatient().getPhone());
        dto.setPatientEmail(a.getPatient().getEmail());

        dto.setDoctorId(a.getDoctor().getId());
        dto.setDoctorName(a.getDoctor().getName());
        dto.setDoctorSpecialization(a.getDoctor().getSpecialization());
        dto.setConsultationFee(a.getDoctor().getConsultationFee());

        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentTime(a.getAppointmentTime());
        dto.setReason(a.getReason());
        dto.setStatus(a.getStatus());
        return dto;
    }
}
