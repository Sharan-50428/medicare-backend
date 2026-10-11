-- ====================================================================
-- MediCare Appointment System - Database Schema DDL
-- Database Name: medicare
-- Compatible with: MySQL 5.7 / 8.0+ / MariaDB / H2
-- ====================================================================

CREATE DATABASE IF NOT EXISTS medicare;
USE medicare;

-- 1. Patients Table
-- Primary Key: id (Auto-Increment)
CREATE TABLE IF NOT EXISTS patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 2. Doctors Table
-- Primary Key: id (Auto-Increment)
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification VARCHAR(100) NOT NULL,
    experience INT NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    consultation_fee DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    INDEX idx_doc_spec (specialization),
    INDEX idx_doc_status (status)
);

-- 3. Doctor Schedules Table
-- Foreign Key: doctor_id references doctors(id) with CASCADE ON DELETE
CREATE TABLE IF NOT EXISTS doctor_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    day VARCHAR(20) NOT NULL,
    start_time VARCHAR(20) NOT NULL,
    end_time VARCHAR(20) NOT NULL,
    INDEX idx_sched_doc (doctor_id),
    CONSTRAINT fk_schedule_doctor 
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

-- 4. Appointments Table
-- Foreign Keys:
-- patient_id references patients(id)
-- doctor_id references doctors(id)
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time VARCHAR(20) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'BOOKED',
    INDEX idx_appt_patient (patient_id),
    INDEX idx_appt_doc_date (doctor_id, appointment_date, appointment_time),
    CONSTRAINT fk_appointment_patient 
        FOREIGN KEY (patient_id) 
        REFERENCES patients(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_appointment_doctor 
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

-- 5. Admins Table
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
);
