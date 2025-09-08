package dev.ash.jobsportal.repository;

import dev.ash.jobsportal.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, Long> {

}
