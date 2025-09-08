package dev.ash.jobsportal.controller;

import dev.ash.jobsportal.dto.ProfileDTO;
import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal AppUser user){
        var profileDTO = profileService.getProfileByUserId(user.getId());
        if(!profileDTO.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profileDTO.get());
    }

    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(@AuthenticationPrincipal AppUser user, @RequestBody ProfileDTO profileDTO) {
        var updatedProfile = profileService.updateProfile(user, profileDTO);
        if(!updatedProfile.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProfile.get());
    }
}
