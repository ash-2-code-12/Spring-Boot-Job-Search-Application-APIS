package dev.ash.jobsportal.repository;

import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.model.Application;
import dev.ash.jobsportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(AppUser user);

    Optional<Application> findByUserAndJob(AppUser user, Job job);
}
