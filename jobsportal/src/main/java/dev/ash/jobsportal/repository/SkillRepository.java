package dev.ash.jobsportal.repository;

import dev.ash.jobsportal.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
