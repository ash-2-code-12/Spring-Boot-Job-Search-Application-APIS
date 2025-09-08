package dev.ash.jobsportal.dto;

import java.time.LocalDate;

public record ExperienceDTO(
        String companyName,
        String role,
        LocalDate fromDate,
        LocalDate toDate
) {}
