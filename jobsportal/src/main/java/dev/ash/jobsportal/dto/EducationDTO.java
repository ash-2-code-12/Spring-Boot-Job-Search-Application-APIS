package dev.ash.jobsportal.dto;

import java.time.LocalDate;

public record EducationDTO(
        String level,
        String institutionName,
        String courseName,
        LocalDate fromDate,
        LocalDate toDate,
        Double resultPercentage
) {}
