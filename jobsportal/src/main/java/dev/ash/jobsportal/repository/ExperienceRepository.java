package dev.ash.jobsportal.repository;

import dev.ash.jobsportal.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

}
