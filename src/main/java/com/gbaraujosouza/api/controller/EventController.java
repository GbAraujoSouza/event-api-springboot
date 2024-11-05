package com.gbaraujosouza.api.controller;

import com.gbaraujosouza.api.domain.event.Event;
import com.gbaraujosouza.api.domain.event.EventRequestDTO;
import com.gbaraujosouza.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Event> create(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("date") Long date,
            @RequestParam("city") String city,
            @RequestParam("state") String State,
            @RequestParam("remote") Boolean remote,
            @RequestParam("eventUrl") String eventUrl,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, State, remote, eventUrl, image);
        Event newEvent = this.eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(newEvent);
    }

}
