package com.hmsapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "room_availability")
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "total_rooms", nullable = false)
    private Long totalRooms;

    @Column(name = "nightly_price", nullable = false)
    private Long nightlyPrice;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

}