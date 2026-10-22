package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.Groomer;
import com.auca.pet_shop_management_system.repository.GroomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroomerService {

    @Autowired
    private GroomerRepository groomerRepository;

    public String save(Groomer groomer) {
        groomerRepository.save(groomer);
        return "Groomer saved successfully";
    }

    public List<Groomer> findAll() {
        return groomerRepository.findAll();
    }

    public Optional<Groomer> findById(UUID id) {
        return groomerRepository.findById(id);
    }

    public String update(UUID id, Groomer groomer) {
        Optional<Groomer> existing = groomerRepository.findById(id);
        if (existing.isEmpty()) {
            return "The groomer with that id does not exist";
        }
        Groomer current = existing.get();
        current.setFirstName(groomer.getFirstName());
        current.setLastName(groomer.getLastName());
        current.setPhone(groomer.getPhone());
        current.setGroomingBay(groomer.getGroomingBay());
        if (groomer.getPetTypes() != null) {
            current.setPetTypes(groomer.getPetTypes());
        }
        groomerRepository.save(current);
        return "Groomer updated successfully";
    }

    public String delete(UUID id) {
        if (!groomerRepository.existsById(id)) {
            return "The groomer with that id does not exist";
        }
        groomerRepository.deleteById(id);
        return "Groomer deleted successfully";
    }
}