package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.GroomingBay;
import com.auca.pet_shop_management_system.service.GroomingBayService;
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
@RequestMapping("/api/grooming-bays")
public class GroomingBayController {

    @Autowired
    private GroomingBayService groomingBayService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody GroomingBay groomingBay) {
        String message = groomingBayService.save(groomingBay);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroomingBay>> findAll() {
        return new ResponseEntity<>(groomingBayService.findAll(), HttpStatus.OK);
    }

   
    @GetMapping("/free")
    public ResponseEntity<List<GroomingBay>> findFree() {
        return new ResponseEntity<>(groomingBayService.findFree(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<GroomingBay> groomingBay = groomingBayService.findById(id);
        if (groomingBay.isPresent()) {
            return new ResponseEntity<>(groomingBay.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The grooming bay with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody GroomingBay groomingBay) {
        String message = groomingBayService.update(id, groomingBay);
        if (message.equals("Grooming bay updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = groomingBayService.delete(id);
        if (message.equals("Grooming bay deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}