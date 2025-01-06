package com.JPA.Member.Entity.Guide.TripList;

import com.JPA.Member.Entity.Guide.Guide.GuideEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="trip_list_table")
public class TripListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private int likesCount = 0;

    @Column
    private int hatesCount = 0;

    @Column
    private int trip_list_hits;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    @ElementCollection
    private List<String> zipcodeList = new ArrayList<>();

    @Column
    @ElementCollection
    private List<String> trip_list = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guideEntity;

    public void increaseLikesCount() {
        this.likesCount++;
    }

    public void decreaseLikesCount() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public void increaseHatesCount() {
        this.hatesCount++;
    }

    public void decreaseHatesCount() {
        if (this.hatesCount > 0) {
            this.hatesCount--;
        }
    }
}