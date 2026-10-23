package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.PetOwner;
import com.auca.pet_shop_management_system.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public String save(PetOwner petOwner) {
        petOwnerRepository.save(petOwner);
        return "Pet owner saved successfully";
    }

    public List<PetOwner> findAll() {
        return petOwnerRepository.findAll();
    }

    public Optional<PetOwner> findById(UUID id) {
        return petOwnerRepository.findById(id);
    }

    public String update(UUID id, PetOwner petOwner) {
        Optional<PetOwner> existing = petOwnerRepository.findById(id);
        if (existing.isEmpty()) {
            return "The pet owner with that id does not exist";
        }
        PetOwner current = existing.get();
        current.setFirstName(petOwner.getFirstName());
        current.setLastName(petOwner.getLastName());
        current.setOwnerCode(petOwner.getOwnerCode());
        current.setPetName(petOwner.getPetName());
        current.setPhone(petOwner.getPhone());
        petOwnerRepository.save(current);
        return "Pet owner updated successfully";
    }

    public String delete(UUID id) {
        if (!petOwnerRepository.existsById(id)) {
            return "The pet owner with that id does not exist";
        }
        petOwnerRepository.deleteById(id);
        return "Pet owner deleted successfully";
    }

    public List<PetOwner> findByLastName(String lastName) {
        return petOwnerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}