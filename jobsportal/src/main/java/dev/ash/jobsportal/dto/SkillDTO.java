package dev.ash.jobsportal.dto;

import java.util.UUID;

public record SkillDTO(
        long id,
        String name,
        String imageUrl
) {}
