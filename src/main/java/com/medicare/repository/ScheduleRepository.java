package com.medicare.repository;

import com.medicare.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScheduleRepository
 * Spring Data JPA Repository providing schedule lookups for Doctors.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long doctorId);

    List<DoctorSchedule> findByDoctorIdAndDayIgnoreCase(Long doctorId, String day);

    boolean existsByDoctorIdAndDayIgnoreCase(Long doctorId, String day);
}
