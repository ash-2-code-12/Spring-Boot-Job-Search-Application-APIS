package dev.ash.jobsportal.controller;

import dev.ash.jobsportal.dto.ApplicationDTO;
import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.service.ApplicationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationsController {
    private final ApplicationsService applicationsService;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<ApplicationDTO> applyToJob(@AuthenticationPrincipal AppUser user,
                                                     @PathVariable long jobId) {
        return ResponseEntity.status(201)
                .body(applicationsService.applyToJob(user, jobId));
    }

    @GetMapping("/applied")
    public ResponseEntity<List<ApplicationDTO>> getMyApplications(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(applicationsService.getUserApplications(user));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> removeApplication(@AuthenticationPrincipal AppUser user,
                                                  @PathVariable long applicationId) {
        boolean deleted = applicationsService.removeApplication(user, applicationId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
