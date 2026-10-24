package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.SessionStatus;
import com.auca.pet_shop_management_system.service.GroomingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/grooming-sessions")
public class GroomingSessionController {

    @Autowired
    private GroomingSessionService groomingSessionService;

    // D1: saves a grooming session; 400 for a past date, 409 when the groomer is already booked, 201 otherwise
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody GroomingSession groomingSession) {
        String message = groomingSessionService.save(groomingSession);
        if (message.equals("Grooming session date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if (message.equals("Groomer is already booked on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    // Returns every grooming session
    @GetMapping("/all")
    public ResponseEntity<List<GroomingSession>> findAll() {
        return new ResponseEntity<>(groomingSessionService.findAll(), HttpStatus.OK);
    }

    // B2: lists grooming sessions in one status, earliest date first
    @GetMapping("/by-status")
    public ResponseEntity<List<GroomingSession>> findByStatus(@RequestParam SessionStatus status) {
        return new ResponseEntity<>(groomingSessionService.findByStatus(status), HttpStatus.OK);
    }

    // B3: lists grooming sessions between two dates, both inclusive; the Strings are parsed here
    @GetMapping("/between")
    public ResponseEntity<List<GroomingSession>> findBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(groomingSessionService.findBetween(startDate, endDate), HttpStatus.OK);
    }

    // Returns one grooming session, or 404 when the id is unknown
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<GroomingSession> groomingSession = groomingSessionService.findById(id);
        if (groomingSession.isPresent()) {
            return new ResponseEntity<>(groomingSession.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The grooming session with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Updates the general data of a grooming session (never the status), or 404 when the id is unknown
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody GroomingSession groomingSession) {
        String message = groomingSessionService.update(id, groomingSession);
        if (message.equals("Grooming session updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    // D2: moves the status along the allowed arrows; 404 unknown id, 400 forbidden move, 200 otherwise
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam SessionStatus status) {
        String message = groomingSessionService.updateStatus(id, status);
        if (message.equals("Grooming session status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.startsWith("Cannot change status from")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    // Deletes a grooming session, or 404 when the id is unknown
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = groomingSessionService.delete(id);
        if (message.equals("Grooming session deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}