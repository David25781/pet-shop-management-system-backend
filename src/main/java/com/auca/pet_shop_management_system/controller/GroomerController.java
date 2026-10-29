package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.Groomer;
import com.auca.pet_shop_management_system.service.GroomerService;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/groomers")
public class GroomerController {

    @Autowired
    private GroomerService groomerService;

    
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Groomer groomer) {
        String message = groomerService.save(groomer);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    
    @GetMapping("/all")
    public ResponseEntity<List<Groomer>> findAll() {
        return new ResponseEntity<>(groomerService.findAll(), HttpStatus.OK);
    }

    
    @GetMapping("/by-pet-type")
    public ResponseEntity<List<Groomer>> findByPetType(@RequestParam String name) {
        return new ResponseEntity<>(groomerService.findByPetTypeName(name), HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Groomer> groomer = groomerService.findById(id);
        if (groomer.isPresent()) {
            return new ResponseEntity<>(groomer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The groomer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Updates a groomer, or 404 when the id is unknown
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Groomer groomer) {
        String message = groomerService.update(id, groomer);
        if (message.equals("Groomer updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    
    @PutMapping("/{groomerId}/pet-types/{petTypeId}")
    public ResponseEntity<String> addPetType(@PathVariable UUID groomerId, @PathVariable UUID petTypeId) {
        String message = groomerService.addPetType(groomerId, petTypeId);
        if (message.equals("Pet type added to the groomer")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("The groomer already has that pet type")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    
    @PatchMapping("/{groomerId}/grooming-bay")
    public ResponseEntity<String> changeGroomingBay(@PathVariable UUID groomerId, @RequestParam int bayNumber) {
        String message = groomerService.changeGroomingBay(groomerId, bayNumber);
        if (message.equals("Grooming bay changed successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("That grooming bay is taken by another groomer")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = groomerService.delete(id);
        if (message.equals("Groomer deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}