package com.medicare.repository;

import com.medicare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * AppointmentRepository
 * Spring Data JPA Repository providing appointment bookings, slots check, and queries.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :appointmentDate AND a.appointmentTime = :appointmentTime AND a.status <> 'CANCELLED'")
    List<Appointment> findActiveAppointmentsByDoctorDateAndTime(
            @Param("doctorId") Long doctorId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentTime") String appointmentTime
    );

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :appointmentDate AND a.appointmentTime = :appointmentTime AND a.status <> 'CANCELLED'")
    boolean isSlotBooked(
            @Param("doctorId") Long doctorId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentTime") String appointmentTime
    );
}
