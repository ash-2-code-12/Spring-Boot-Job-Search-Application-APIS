package dev.ash.jobsportal.service;

import dev.ash.jobsportal.dto.ApplicationDTO;
import dev.ash.jobsportal.dto.ApplicationStatus;
import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.model.Application;
import dev.ash.jobsportal.model.Job;
import dev.ash.jobsportal.repository.ApplicationRepository;
import dev.ash.jobsportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationsService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationDTO applyToJob(AppUser user, long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not Found"));
        Optional<Application> existing = applicationRepository.findByUserAndJob(user, job);
        if(existing.isPresent()) throw new RuntimeException("Already applied to this job");

        Application newApplication = new Application();
        newApplication.setUser(user);
        newApplication.setJob(job);
        newApplication.setStatus((ApplicationStatus.APPLIED));

        Application saved = applicationRepository.save(newApplication);
        return mapToDTO(saved);
    }

    public List<ApplicationDTO> getUserApplications(AppUser user) {
        return applicationRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public boolean removeApplication(AppUser user, long applicationId) {
        return applicationRepository.findById(applicationId)
                .filter(app -> app.getUser().getId().equals(user.getId()))
                .map(app -> {
                    applicationRepository.delete(app);
                    return true;
                })
                .orElse(false);  // ensure the user can only delete if the application is their own
    }

    private ApplicationDTO mapToDTO(Application app) {
        return new ApplicationDTO(
                app.getId(),
                app.getJob().getId(),
                app.getJob().getTitle(),
                app.getStatus()
        );
    }


}
