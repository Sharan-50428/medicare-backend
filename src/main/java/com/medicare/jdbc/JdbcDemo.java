package com.medicare.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * =========================================================================================
 * B.TECH SYLLABUS DEMO: CORE JAVA DATABASE CONNECTIVITY (JDBC) DEMONSTRATION
 * =========================================================================================
 * 
 * This class demonstrates fundamental JDBC concepts as required by university syllabus:
 * 1. Loading the JDBC Driver
 * 2. Establishing a Database Connection using DriverManager
 * 3. Creating and executing SQL statements with PreparedStatement (preventing SQL Injection)
 * 4. Inserting records (INSERT) and handling affected row counts
 * 5. Querying records (SELECT) and iterating through the ResultSet
 * 6. Proper resource management and exception handling using try-with-resources
 * 
 * -----------------------------------------------------------------------------------------
 * HOW JDBC WORKS UNDERNEATH SPRING BOOT & SPRING DATA JPA:
 * -----------------------------------------------------------------------------------------
 * Spring Data JPA and Hibernate DO NOT replace JDBC; they build directly on top of it:
 * 
 * [ Your Java Code / Service Layer ]
 *             │
 *             ▼
 * [ Spring Data JPA Repositories (e.g., DoctorRepository.save(), findById()) ]
 *             │
 *             ▼
 * [ Hibernate / JPA Entity Manager (Object-Relational Mapping / ORM) ]
 *   Translates Java Objects <--> SQL Queries automatically
 *             │
 *             ▼
 * [ Java Database Connectivity (JDBC) - java.sql.* ]
 *   Executes Connection, PreparedStatement, ResultSet underneath
 *             │
 *             ▼
 * [ MySQL JDBC Driver (mysql-connector-j) ]
 *   Sends raw MySQL network packets over TCP/IP port 3306
 *             │
 *             ▼
 * [ MySQL Database Server (tables: doctors, patients, appointments) ]
 * =========================================================================================
 */
public class JdbcDemo {

    // Database Connection Parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/medicare?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root"; // Update to your local MySQL password

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("       MEDICARE APPOINTMENT SYSTEM - CORE JDBC DEMONSTRATION   ");
        System.out.println("===============================================================");

        try {
            // STEP 1: Load and Register the JDBC Driver (Optional in modern JDBC 4.x, but taught in syllabus)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[STEP 1] MySQL JDBC Driver registered successfully.");

            // STEP 2: Establish connection using DriverManager (Using try-with-resources for auto-closing)
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                System.out.println("[STEP 2] Successfully connected to MySQL database: medicare");

                // STEP 3: Execute INSERT using PreparedStatement (Prevents SQL Injection)
                insertSampleDoctor(connection, "Dr. Priya Verma", "Dermatologist", "MBBS, MD", 9, "9876543210", "priya.verma@medicare.com", 600.0, "AVAILABLE");

                // STEP 4: Execute SELECT using PreparedStatement and iterate over ResultSet
                fetchAndDisplayDoctors(connection);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            System.out.println("\nNote for Viva: If MySQL server is not currently running locally, this SQLException is expected.");
            System.out.println("The Spring Boot application can also run with the embedded H2 database profile for standalone demonstration.");
        }
    }

    /**
     * Demonstrates an INSERT operation using PreparedStatement.
     */
    public static void insertSampleDoctor(Connection conn, String name, String spec, String qual, 
                                          int exp, String phone, String email, double fee, String status) throws SQLException {
        String insertSql = "INSERT INTO doctors (name, specialization, qualification, experience, phone, email, consultation_fee, status) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            // Binding parameters to placeholders '?' by 1-based index
            pstmt.setString(1, name);
            pstmt.setString(2, spec);
            pstmt.setString(3, qual);
            pstmt.setInt(4, exp);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);
            pstmt.setDouble(7, fee);
            pstmt.setString(8, status);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("\n[STEP 3 - INSERT]");
            System.out.println("Executed: " + insertSql);
            System.out.println("Rows affected: " + rowsAffected);

            // Retrieve auto-generated primary key
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long newDoctorId = generatedKeys.getLong(1);
                    System.out.println("Newly inserted Doctor ID (Primary Key): " + newDoctorId);
                }
            }
        }
    }

    /**
     * Demonstrates a SELECT query using PreparedStatement and iterating through the ResultSet.
     */
    public static void fetchAndDisplayDoctors(Connection conn) throws SQLException {
        String querySql = "SELECT id, name, specialization, qualification, experience, consultation_fee, status FROM doctors";

        System.out.println("\n[STEP 4 - SELECT & RESULTSET]");
        System.out.println("Executing query: " + querySql);

        try (PreparedStatement pstmt = conn.prepareStatement(querySql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("------------------------------------------------------------------------------------");
            System.out.printf("%-5s | %-20s | %-16s | %-12s | %-6s | %-8s | %-10s%n",
                    "ID", "Doctor Name", "Specialization", "Degree", "Exp(Y)", "Fee(Rs)", "Status");
            System.out.println("------------------------------------------------------------------------------------");

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String qualification = rs.getString("qualification");
                int experience = rs.getInt("experience");
                double fee = rs.getDouble("consultation_fee");
                String status = rs.getString("status");

                System.out.printf("%-5d | %-20s | %-16s | %-12s | %-6d | %-8.2f | %-10s%n",
                        id, name, specialization, qualification, experience, fee, status);
            }
            System.out.println("------------------------------------------------------------------------------------");
        }
    }
}
