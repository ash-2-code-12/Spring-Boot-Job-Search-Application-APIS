package dev.ash.jobsportal.dto;

import java.util.List;
import java.util.UUID;

public record ProfileDTO(
        long id,
        String title,
        String profileImgUrl,
        String mobileNo,
        String about,
        List<String> userSkills,
        List<ExperienceDTO> experienceList,
        List<EducationDTO> educationList,
        List<JobDTO> appliedJobsList
) {}
