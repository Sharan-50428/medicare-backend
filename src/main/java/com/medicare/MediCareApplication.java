package com.medicare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MediCare Appointment System - Main Spring Boot Application Entry Point.
 * 
 * Features:
 * - Clean Layered Architecture: Controller -> Service -> Repository -> Entity -> MySQL
 * - RESTful APIs for Patients, Doctors, Doctor Schedules, Appointments, and Admin
 * - Comprehensive Validation & Custom Exception Handling
 * - Pre-configured CORS support for React Frontend (http://localhost:5173)
 */
@SpringBootApplication
public class MediCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediCareApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   MediCare Backend Server Started Successfully!  ");
        System.out.println("   Access REST APIs at: http://localhost:8080/api ");
        System.out.println("=================================================");
    }
}
