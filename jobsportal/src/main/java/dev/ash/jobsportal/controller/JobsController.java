package dev.ash.jobsportal.controller;

import dev.ash.jobsportal.dto.JobDTO;
import dev.ash.jobsportal.service.JobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobsController {
    private final JobsService jobsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllJobs() {
        List<JobDTO> jobs = jobsService.getAllJobs();
        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobs);
        response.put("total", jobs.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJobById(@PathVariable long id) {
        return jobsService.getJobDetails(id)
                .map(ResponseEntity::ok) // if present, return 200 OK with the body
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // if empty, return 404
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createdJob = jobsService.createJob(jobDTO);
        return ResponseEntity.status(201).body(createdJob);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> createJobs(@RequestBody List<JobDTO> jobs) {
        int savedJobs = jobsService.createJobs(jobs);
        return ResponseEntity.status(HttpStatus.CREATED).body("All " + savedJobs +" jobs saved");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobDTO> updateJob(@PathVariable long id, @RequestBody JobDTO jobDTO) {
        return jobsService.updateJob(id, jobDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
