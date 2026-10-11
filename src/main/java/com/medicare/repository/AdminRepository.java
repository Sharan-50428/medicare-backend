package com.medicare.repository;

import com.medicare.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AdminRepository
 * Spring Data JPA Repository providing lookups for Admin credentials.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByEmail(String email);
}
