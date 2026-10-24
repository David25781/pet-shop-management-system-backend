package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.domain.ProductSale;
import com.auca.pet_shop_management_system.service.ProductSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-sales")
public class ProductSaleController {

    @Autowired
    private ProductSaleService productSaleService;

    // E1: writes a product sale for a FINISHED grooming session; 404 unknown session, 400 not FINISHED, 201 saved
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID groomingSessionId, @RequestBody ProductSale productSale) {
        String message = productSaleService.save(groomingSessionId, productSale);
        if (message.equals("Product sale saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("Product sales are only for FINISHED grooming sessions")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    // Returns every product sale
    @GetMapping("/all")
    public ResponseEntity<List<ProductSale>> findAll() {
        return new ResponseEntity<>(productSaleService.findAll(), HttpStatus.OK);
    }

    // E2: product sale history of a pet owner across all grooming sessions, newest sold date first
    @GetMapping("/pet-owner/{ownerCode}")
    public ResponseEntity<List<ProductSale>> findByOwnerCode(@PathVariable String ownerCode) {
        return new ResponseEntity<>(productSaleService.findByOwnerCode(ownerCode), HttpStatus.OK);
    }

    // E3: deletes a product sale, or 404 when the id is unknown
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = productSaleService.delete(id);
        if (message.equals("Product sale deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}