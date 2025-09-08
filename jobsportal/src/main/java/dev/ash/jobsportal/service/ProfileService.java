package dev.ash.jobsportal.service;

import dev.ash.jobsportal.dto.*;
import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.model.Education;
import dev.ash.jobsportal.model.Experience;
import dev.ash.jobsportal.model.Profile;
import dev.ash.jobsportal.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    // this is called during registration so a profile record is created for that user
    public void createEmptyProfileForUser(AppUser user) {
        log.info("entered profile service");
        Profile profile = new Profile();

        profile.setUser(user);
        profile.setTitle("");
        profile.setProfileImgUrl("");
        profile.setMobileNo("");
        profile.setAbout("");
        profile.setUserSkills(List.of());
        profile.setExperienceList(List.of());
        profile.setEducationList(List.of());
        profile.setAppliedJobsList(List.of());
        log.info("created profile object");
        profileRepository.save(profile);
        log.info("profile object saved");
    }

    public Optional<ProfileDTO> getProfileByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .map(this::mapToDTO);
    }

    public Optional<ProfileDTO> updateProfile(AppUser user, ProfileDTO dto) {
        return profileRepository.findByUserId(user.getId())
                .map(profile -> {
                    // Update simple fields if present
                    if(dto.title() != null) profile.setTitle(dto.title());
                    if(dto.profileImgUrl() != null) profile.setProfileImgUrl(dto.profileImgUrl());
                    if(dto.mobileNo() != null) profile.setMobileNo(dto.mobileNo());
                    if(dto.about() != null) profile.setAbout(dto.about());
                    if(dto.userSkills() != null) profile.setUserSkills(dto.userSkills());
                    // Update Experience list
                    if(dto.experienceList() != null) {
                        List<Experience> updatedExperiences = dto.experienceList().stream()
                                .map(e -> {
                                    Experience exp = new Experience();
                                    exp.setCompanyName(e.companyName());
                                    exp.setRole(e.role());
                                    exp.setFromDate(e.fromDate());
                                    exp.setToDate(e.toDate());
                                    return exp;
                                }).toList();
                        profile.setExperienceList(updatedExperiences);
                    }
                    // Update Education list
                    if(dto.educationList() != null) {
                        List<Education> updatedEducation = dto.educationList().stream()
                                .map(ed -> {
                                    Education edu = new Education();
                                    edu.setLevel(ed.level());
                                    edu.setInstitutionName(ed.institutionName());
                                    edu.setCourseName(ed.courseName());
                                    edu.setFromDate(ed.fromDate());
                                    edu.setToDate(ed.toDate());
                                    edu.setResultPercentage(ed.resultPercentage());
                                    return edu;
                                }).toList();
                        profile.setEducationList(updatedEducation);
                    }
                    // Save profile (cascades to Experience & Education)
                    profileRepository.save(profile);
                    return mapToDTO(profile);
                });
    }

    private ProfileDTO mapToDTO(Profile profile) {
        return new ProfileDTO(
                profile.getId(),
                profile.getTitle(),
                profile.getProfileImgUrl(),
                profile.getMobileNo(),
                profile.getAbout(),
                profile.getUserSkills(),
                profile.getExperienceList().stream()
                        .map(e -> new ExperienceDTO(
                                e.getCompanyName(),
                                e.getRole(),
                                e.getFromDate(),
                                e.getToDate()
                        ))
                        .toList(),
                profile.getEducationList().stream()
                        .map(ed -> new EducationDTO(
                                ed.getLevel(),
                                ed.getInstitutionName(),
                                ed.getCourseName(),
                                ed.getFromDate(),
                                ed.getToDate(),
                                ed.getResultPercentage()
                        ))
                        .toList(),
                profile.getAppliedJobsList().stream()
                        .map(job -> new JobDTO(
                                job.getId(),
                                job.getTitle(),
                                job.getCompanyLogoUrl(),
                                job.getCompanyWebsiteUrl(),
                                job.getJobDescription(), // make sure this exists in Job entity
                                job.getLocation(),
                                job.getEmploymentType(),
                                job.getPackagePerAnnum(),
                                job.getRating(), // int
                                job.getSkills() != null
                                        ? job.getSkills().stream()
                                        .map(s -> new SkillDTO(s.getId(), s.getName(), s.getImageUrl()))
                                        .toList()
                                        : List.of(), // empty list if no skills
                                job.getLifeAtCompany() != null
                                        ? new LifeAtCompanyDTO(
                                        job.getLifeAtCompany().getDescription(),
                                        job.getLifeAtCompany().getImageUrl()
                                )
                                        : null
                        ))
                        .toList()
        );
    }
}