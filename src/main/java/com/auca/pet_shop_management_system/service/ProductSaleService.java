package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.ProductSale;
import com.auca.pet_shop_management_system.domain.SessionStatus;
import com.auca.pet_shop_management_system.repository.GroomingSessionRepository;
import com.auca.pet_shop_management_system.repository.ProductSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductSaleService {

    @Autowired
    private ProductSaleRepository productSaleRepository;

    @Autowired
    private GroomingSessionRepository groomingSessionRepository;

    public String save(UUID groomingSessionId, ProductSale productSale) {
        Optional<GroomingSession> sessionOptional = groomingSessionRepository.findById(groomingSessionId);
        if (sessionOptional.isEmpty()) {
            return "The grooming session with that id does not exist";
        }
        GroomingSession groomingSession = sessionOptional.get();
        if (groomingSession.getStatus() != SessionStatus.FINISHED) {
            return "Product sales are only for FINISHED grooming sessions";
        }
        productSale.setId(null);
        productSale.setSoldDate(LocalDate.now());
        productSale.setGroomingSession(groomingSession);
        productSaleRepository.save(productSale);
        return "Product sale saved successfully";
    }

    public List<ProductSale> findAll() {
        return productSaleRepository.findAll();
    }

    public List<ProductSale> findByOwnerCode(String ownerCode) {
        return productSaleRepository.findProductSalesByOwnerCode(ownerCode);
    }

    public String delete(UUID id) {
        if (!productSaleRepository.existsById(id)) {
            return "The product sale with that id does not exist";
        }
        productSaleRepository.deleteById(id);
        return "Product sale deleted successfully";
    }
}