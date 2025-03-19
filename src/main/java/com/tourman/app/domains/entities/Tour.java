package com.tourman.app.domains.entities;

import com.tourman.app.domains.status.TourStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tourId;

    @Column(nullable = false)
    private String tourName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String tourer;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String maxParticipants;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private TourStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
    private TourProvider provider;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tour")
    private Set<Booking> bookings = new HashSet<>();
}