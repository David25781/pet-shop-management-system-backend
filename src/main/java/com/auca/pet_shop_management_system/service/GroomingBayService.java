package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.GroomingBay;
import com.auca.pet_shop_management_system.repository.GroomingBayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroomingBayService {

    @Autowired
    private GroomingBayRepository groomingBayRepository;

    public String save(GroomingBay groomingBay) {
        groomingBayRepository.save(groomingBay);
        return "Grooming bay saved successfully";
    }

    public List<GroomingBay> findAll() {
        return groomingBayRepository.findAll();
    }

    public Optional<GroomingBay> findById(UUID id) {
        return groomingBayRepository.findById(id);
    }

    public String update(UUID id, GroomingBay groomingBay) {
        Optional<GroomingBay> existing = groomingBayRepository.findById(id);
        if (existing.isEmpty()) {
            return "The grooming bay with that id does not exist";
        }
        GroomingBay current = existing.get();
        current.setBayNumber(groomingBay.getBayNumber());
        current.setName(groomingBay.getName());
        groomingBayRepository.save(current);
        return "Grooming bay updated successfully";
    }

    public String delete(UUID id) {
        if (!groomingBayRepository.existsById(id)) {
            return "The grooming bay with that id does not exist";
        }
        groomingBayRepository.deleteById(id);
        return "Grooming bay deleted successfully";
    }
}