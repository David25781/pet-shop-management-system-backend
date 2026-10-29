package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.PetType;
import com.auca.pet_shop_management_system.service.PetTypeService;
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
@RequestMapping("/api/pet-types")
public class PetTypeController {

    @Autowired
    private PetTypeService petTypeService;

    
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody PetType petType) {
        String message = petTypeService.save(petType);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    
    @GetMapping("/all")
    public ResponseEntity<List<PetType>> findAll() {
        return new ResponseEntity<>(petTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<PetType>> findUnused() {
        return new ResponseEntity<>(petTypeService.findUnused(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<PetType> petType = petTypeService.findById(id);
        if (petType.isPresent()) {
            return new ResponseEntity<>(petType.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pet type with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PetType petType) {
        String message = petTypeService.update(id, petType);
        if (message.equals("Pet type updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = petTypeService.delete(id);
        if (message.equals("Pet type deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}