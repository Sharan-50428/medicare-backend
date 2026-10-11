package com.medicare.repository;

import com.medicare.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DoctorRepository
 * Spring Data JPA Repository providing CRUD and search operations for Doctor entity.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    List<Doctor> findByStatus(String status);

    @Query("SELECT d FROM Doctor d WHERE " +
           "LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(d.specialization) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Doctor> searchByNameOrSpecialization(@Param("query") String query);

    @Query("SELECT d FROM Doctor d WHERE " +
           "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:specialization IS NULL OR LOWER(d.specialization) = LOWER(:specialization))")
    List<Doctor> filterDoctors(@Param("name") String name, @Param("specialization") String specialization);
}
