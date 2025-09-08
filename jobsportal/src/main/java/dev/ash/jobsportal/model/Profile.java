package dev.ash.jobsportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private String title;
    private String profileImgUrl;
    private String mobileNo;

    @Column(length = 100)
    private String about;

    @ElementCollection
    private List<String> userSkills;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Experience> experienceList;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Education> educationList;

    @ManyToMany
    private List<Job> appliedJobsList;

}
