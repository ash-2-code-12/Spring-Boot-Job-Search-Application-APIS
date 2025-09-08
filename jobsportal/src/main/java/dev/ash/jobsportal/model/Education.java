package dev.ash.jobsportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String level;
    private String institutionName;
    private String courseName;
    private LocalDate fromDate;
    private LocalDate toDate;

    @Min(value = 0, message = "Result percentage cannot be negative")
    @Max(value = 100, message = "Result percentage cannot exceed 100")
    private Double resultPercentage;
}
