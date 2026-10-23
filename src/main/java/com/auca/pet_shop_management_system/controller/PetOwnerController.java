package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.PetOwner;
import com.auca.pet_shop_management_system.service.PetOwnerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pet-owners")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    // Saves a new pet owner and answers 201 Created
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody PetOwner petOwner) {
        String message = petOwnerService.save(petOwner);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Returns every pet owner
    @GetMapping("/all")
    public ResponseEntity<List<PetOwner>> findAll() {
        return new ResponseEntity<>(petOwnerService.findAll(), HttpStatus.OK);
    }

    // B1: finds pet owners by last name, case-insensitive, sorted by first name
    @GetMapping("/by-last-name")
    public ResponseEntity<List<PetOwner>> findByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(petOwnerService.findByLastName(lastName), HttpStatus.OK);
    }

    // C4: lists each pet owner of a groomer once, or 404 when the groomer id is unknown
    @GetMapping("/of-groomer/{groomerId}")
    public ResponseEntity<?> findByGroomer(@PathVariable UUID groomerId) {
        Optional<List<PetOwner>> petOwners = petOwnerService.findByGroomer(groomerId);
        if (petOwners.isPresent()) {
            return new ResponseEntity<>(petOwners.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The groomer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Returns one pet owner, or 404 when the id is unknown
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<PetOwner> petOwner = petOwnerService.findById(id);
        if (petOwner.isPresent()) {
            return new ResponseEntity<>(petOwner.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pet owner with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Updates a pet owner, or 404 when the id is unknown
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PetOwner petOwner) {
        String message = petOwnerService.update(id, petOwner);
        if (message.equals("Pet owner updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    // Deletes a pet owner, or 404 when the id is unknown
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = petOwnerService.delete(id);
        if (message.equals("Pet owner deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}