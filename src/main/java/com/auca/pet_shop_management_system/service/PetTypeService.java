package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.PetType;
import com.auca.pet_shop_management_system.repository.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    public String save(PetType petType) {
        petTypeRepository.save(petType);
        return "Pet type saved successfully";
    }

    public List<PetType> findAll() {
        return petTypeRepository.findAll();
    }

    public Optional<PetType> findById(UUID id) {
        return petTypeRepository.findById(id);
    }

    public String update(UUID id, PetType petType) {
        Optional<PetType> existing = petTypeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The pet type with that id does not exist";
        }
        PetType current = existing.get();
        current.setName(petType.getName());
        petTypeRepository.save(current);
        return "Pet type updated successfully";
    }

    public String delete(UUID id) {
        if (!petTypeRepository.existsById(id)) {
            return "The pet type with that id does not exist";
        }
        petTypeRepository.deleteById(id);
        return "Pet type deleted successfully";
    }

    public List<PetType> findUnused() {
        return petTypeRepository.findUnusedPetTypes();
    }
}