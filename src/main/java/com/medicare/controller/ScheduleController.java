package com.medicare.controller;

import com.medicare.dto.ApiResponseDTO;
import com.medicare.dto.ScheduleDTO;
import com.medicare.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Doctor Schedule endpoints.
 * Base URL: /api/schedules
 */
@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponseDTO<List<ScheduleDTO>>> getSchedulesByDoctorId(@PathVariable Long doctorId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(ApiResponseDTO.success("Doctor schedules retrieved", schedules));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ScheduleDTO>> addSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO created = scheduleService.addSchedule(scheduleDTO);
        return new ResponseEntity<>(
                ApiResponseDTO.success("Schedule added successfully", created),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ScheduleDTO>> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO updated = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(ApiResponseDTO.success("Schedule updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Schedule deleted successfully", null));
    }
}
