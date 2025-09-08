package dev.ash.jobsportal.dto;

import java.util.List;
import java.util.UUID;

public record JobDTO(
        long id,
        String title,
        String companyLogoUrl,
        String companyWebsiteUrl,
        String jobDescription,
        String location,
        String employmentType,
        String packagePerAnnum,
        int rating,
        List<SkillDTO> skills,
        LifeAtCompanyDTO lifeAtCompany
) {}
