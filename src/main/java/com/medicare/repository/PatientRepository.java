package com.medicare.repository;

import com.medicare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * PatientRepository
 * Spring Data JPA Repository providing CRUD operations for Patient entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);
}
