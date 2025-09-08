package dev.ash.jobsportal.repository;

import dev.ash.jobsportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, Long> {
    // Fetch top 3 jobs with the same employment type, excluding the current job
    List<Job> findTop3ByEmploymentTypeAndIdNot(String employmentType, Long excludedJobId);
}
