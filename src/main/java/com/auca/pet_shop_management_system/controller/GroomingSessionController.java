package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.service.GroomingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/grooming-sessions")
public class GroomingSessionController {

    @Autowired
    private GroomingSessionService groomingSessionService;

    // Saves a new grooming session and answers 201 Created
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody GroomingSession groomingSession) {
        String message = groomingSessionService.save(groomingSession);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Returns every grooming session
    @GetMapping("/all")
    public ResponseEntity<List<GroomingSession>> findAll() {
        return new ResponseEntity<>(groomingSessionService.findAll(), HttpStatus.OK);
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

    // Updates a grooming session, or 404 when the id is unknown
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody GroomingSession groomingSession) {
        String message = groomingSessionService.update(id, groomingSession);
        if (message.equals("Grooming session updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
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