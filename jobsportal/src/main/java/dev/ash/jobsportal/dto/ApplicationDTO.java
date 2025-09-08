package dev.ash.jobsportal.dto;

import java.util.UUID;

public record ApplicationDTO(
        long id,
        long jobId,
        String jobTitle,
        ApplicationStatus status
) {}
