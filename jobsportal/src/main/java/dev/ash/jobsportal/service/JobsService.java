package dev.ash.jobsportal.service;

import dev.ash.jobsportal.dto.JobDTO;
import dev.ash.jobsportal.dto.LifeAtCompanyDTO;
import dev.ash.jobsportal.dto.SkillDTO;
import dev.ash.jobsportal.model.Job;
import dev.ash.jobsportal.model.LifeAtCompany;
import dev.ash.jobsportal.model.Skill;
import dev.ash.jobsportal.repository.JobRepository;
import dev.ash.jobsportal.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobsService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public List<JobDTO> getAllJobs(){
        return jobRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<Map<String, Object>> getJobDetails(long jobId) {
        return jobRepository.findById(jobId)
                .map(job -> {
                    JobDTO mainJob = mapToDTO(job);

                    List<JobDTO> similarJobs = jobRepository.findTop3ByEmploymentTypeAndIdNot(job.getEmploymentType(), job.getId())
                            .stream()
                            .map(this::mapToDTO)
                            .collect(Collectors.toList());

                    Map<String, Object> response = new HashMap<>();
                    response.put("job_details", mainJob);
                    response.put("similar_jobs", similarJobs);
                    return response;
                });
    }

    public JobDTO createJob(JobDTO dto) {
        Job job = new Job();
        job.setTitle(dto.title());
        job.setCompanyLogoUrl(dto.companyLogoUrl());
        job.setCompanyWebsiteUrl(dto.companyWebsiteUrl());
        job.setJobDescription(dto.jobDescription());
        job.setLocation(dto.location());
        job.setEmploymentType(dto.employmentType());
        job.setPackagePerAnnum(dto.packagePerAnnum());
        job.setRating(dto.rating());

        // Map LifeAtCompany
        if(dto.lifeAtCompany() != null) {
            LifeAtCompany life = new LifeAtCompany();
            life.setDescription(dto.lifeAtCompany().description());
            life.setImageUrl(dto.lifeAtCompany().imageUrl());
            job.setLifeAtCompany(life);
        }

        // Map skills — DO NOT save manually, rely on cascade
        if(dto.skills() != null) {
            List<Skill> skills = dto.skills().stream().map(s -> {
                Skill newSkill = new Skill();
                newSkill.setName(s.name());
                newSkill.setImageUrl(s.imageUrl());
                return newSkill;
            }).toList();
            job.setSkills(skills);
        }

        // Save the job — cascades skills automatically
        Job saved = jobRepository.save(job);
        return mapToDTO(saved);
    }

    public int createJobs(List<JobDTO> jobs) {
        for (JobDTO job : jobs) {
            createJob(job);
        }
        return jobs.size();
    }


    public Optional<JobDTO> updateJob(long jobId, JobDTO dto) {
        return jobRepository.findById(jobId)
                .map(job -> {
                    if (dto.title() != null) job.setTitle(dto.title());
                    if (dto.companyLogoUrl() != null) job.setCompanyLogoUrl(dto.companyLogoUrl());
                    if (dto.companyWebsiteUrl() != null) job.setCompanyWebsiteUrl(dto.companyWebsiteUrl());
                    if (dto.jobDescription() != null) job.setJobDescription(dto.jobDescription());
                    if (dto.location() != null) job.setLocation(dto.location());
                    if (dto.employmentType() != null) job.setEmploymentType(dto.employmentType());
                    if (dto.packagePerAnnum() != null) job.setPackagePerAnnum(dto.packagePerAnnum());
                    if (dto.skills() != null) {
                        List<Skill> updatedSkills = dto.skills().stream()
                                .map(s -> new Skill(s.id(), s.name(), s.imageUrl()))
                                .toList();
                        job.setSkills(updatedSkills);
                    }
                    if (dto.lifeAtCompany() != null) {
                        LifeAtCompany lac = new LifeAtCompany(
                                dto.lifeAtCompany().description(),
                                dto.lifeAtCompany().imageUrl()
                        );
                        job.setLifeAtCompany(lac);
                    }

                    jobRepository.save(job);
                    return mapToDTO(job);
                });
    }


    private JobDTO mapToDTO(Job job) {
        return new JobDTO(
                job.getId(),
                job.getTitle(),
                job.getCompanyLogoUrl(),
                job.getCompanyWebsiteUrl(),
                job.getJobDescription(),
                job.getLocation(),
                job.getEmploymentType(),
                job.getPackagePerAnnum(),
                job.getRating(),
                job.getSkills() != null
                        ? job.getSkills().stream()
                        .map(s -> new SkillDTO(s.getId(), s.getName(), s.getImageUrl()))
                        .toList()
                        : List.of(),
                job.getLifeAtCompany() != null
                        ? new LifeAtCompanyDTO(job.getLifeAtCompany().getDescription(),
                        job.getLifeAtCompany().getImageUrl())
                        : null
        );
    }

}
