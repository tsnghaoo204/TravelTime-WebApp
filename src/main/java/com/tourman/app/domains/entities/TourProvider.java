package com.tourman.app.domains.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tour_providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    private String img_path;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "provider")
    private Set<Tour> tours = new HashSet<>();

}

