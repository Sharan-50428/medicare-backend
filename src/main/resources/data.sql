-- ====================================================================
-- MediCare Appointment System - Initial Sample Seed Data
-- ====================================================================

-- 1. Insert Admins (default admin credentials: admin / admin123)
INSERT INTO admins (id, username, password, email, role) VALUES 
(1, 'admin', 'admin123', 'admin@medicare.com', 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

-- 2. Insert Doctors
INSERT INTO doctors (id, name, specialization, qualification, experience, phone, email, consultation_fee, status) VALUES
(1, 'Dr. Rahul Sharma', 'Cardiologist', 'MBBS, MD, DM (Cardiology)', 12, '9876543210', 'rahul.sharma@medicare.com', 800.0, 'AVAILABLE'),
(2, 'Dr. Priya Verma', 'Dermatologist', 'MBBS, MD (Dermatology)', 8, '9876543211', 'priya.verma@medicare.com', 600.0, 'AVAILABLE'),
(3, 'Dr. Ananya Iyer', 'Pediatrician', 'MBBS, DCH, MD (Pediatrics)', 10, '9876543212', 'ananya.iyer@medicare.com', 500.0, 'AVAILABLE'),
(4, 'Dr. Vikram Malhotra', 'Orthopedic Surgeon', 'MBBS, MS (Orthopedics), M.Ch', 15, '9876543213', 'vikram.m@medicare.com', 900.0, 'AVAILABLE'),
(5, 'Dr. Sneha Kulkarni', 'Neurologist', 'MBBS, MD, DM (Neurology)', 11, '9876543214', 'sneha.k@medicare.com', 850.0, 'AVAILABLE'),
(6, 'Dr. Rajesh Patel', 'General Physician', 'MBBS, MD (Internal Medicine)', 14, '9876543215', 'rajesh.patel@medicare.com', 400.0, 'AVAILABLE')
ON DUPLICATE KEY UPDATE name=name;

-- 3. Insert Doctor Schedules
-- Dr. Rahul Sharma (Cardiologist) - Mon, Tue, Wed, Thu, Fri
INSERT INTO doctor_schedules (id, doctor_id, day, start_time, end_time) VALUES
(1, 1, 'Monday', '10:00 AM', '05:00 PM'),
(2, 1, 'Tuesday', '10:00 AM', '05:00 PM'),
(3, 1, 'Wednesday', '10:00 AM', '05:00 PM'),
(4, 1, 'Thursday', '10:00 AM', '05:00 PM'),
(5, 1, 'Friday', '10:00 AM', '05:00 PM'),

-- Dr. Priya Verma (Dermatologist) - Mon, Wed, Sat
(6, 2, 'Monday', '11:00 AM', '04:00 PM'),
(7, 2, 'Wednesday', '11:00 AM', '04:00 PM'),
(8, 2, 'Saturday', '09:00 AM', '01:00 PM'),

-- Dr. Ananya Iyer (Pediatrician) - Mon, Tue, Thu, Fri
(9, 3, 'Monday', '09:00 AM', '02:00 PM'),
(10, 3, 'Tuesday', '09:00 AM', '02:00 PM'),
(11, 3, 'Thursday', '09:00 AM', '02:00 PM'),
(12, 3, 'Friday', '09:00 AM', '02:00 PM'),

-- Dr. Vikram Malhotra (Orthopedic) - Tue, Thu, Sat
(13, 4, 'Tuesday', '10:00 AM', '04:00 PM'),
(14, 4, 'Thursday', '10:00 AM', '04:00 PM'),
(15, 4, 'Saturday', '10:00 AM', '02:00 PM'),

-- Dr. Sneha Kulkarni (Neurologist) - Wed, Fri, Sat
(16, 5, 'Wednesday', '12:00 PM', '06:00 PM'),
(17, 5, 'Friday', '12:00 PM', '06:00 PM'),
(18, 5, 'Saturday', '10:00 AM', '02:00 PM'),

-- Dr. Rajesh Patel (General Physician) - Mon, Tue, Wed, Thu, Fri, Sat
(19, 6, 'Monday', '09:00 AM', '06:00 PM'),
(20, 6, 'Tuesday', '09:00 AM', '06:00 PM'),
(21, 6, 'Wednesday', '09:00 AM', '06:00 PM'),
(22, 6, 'Thursday', '09:00 AM', '06:00 PM'),
(23, 6, 'Friday', '09:00 AM', '06:00 PM'),
(24, 6, 'Saturday', '09:00 AM', '01:00 PM')
ON DUPLICATE KEY UPDATE day=day;

-- 4. Insert Sample Patients
INSERT INTO patients (id, name, age, gender, phone, email, address, password) VALUES
(1, 'Rohan Mehta', 28, 'Male', '9811223344', 'rohan@gmail.com', 'Flat 402, Green Glen Layout, Bangalore', 'rohan123'),
(2, 'Pooja Nair', 34, 'Female', '9822334455', 'pooja@gmail.com', '12, Palm Meadows, Whitefield, Bangalore', 'pooja123'),
(3, 'Amitabh Sen', 52, 'Male', '9833445566', 'amitabh@gmail.com', '74/B, Park Street, Kolkata', 'amitabh123')
ON DUPLICATE KEY UPDATE name=name;

-- 5. Insert Sample Appointments
INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, reason, status) VALUES
(1, 1, 1, '2026-10-15', '10:00 AM', 'Routine cardiac health checkup and ECG consultation', 'CONFIRMED'),
(2, 2, 2, '2026-10-17', '11:00 AM', 'Skin allergy and dermatological consultation', 'BOOKED'),
(3, 1, 6, '2026-10-05', '10:30 AM', 'Seasonal flu and recurring mild fever', 'COMPLETED'),
(4, 3, 4, '2026-10-12', '02:00 PM', 'Knee joint pain after morning jogging', 'BOOKED')
ON DUPLICATE KEY UPDATE reason=reason;
