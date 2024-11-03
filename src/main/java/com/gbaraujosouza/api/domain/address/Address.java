package com.gbaraujosouza.api.domain.address;

import com.gbaraujosouza.api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    private String state;
    private String city;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
