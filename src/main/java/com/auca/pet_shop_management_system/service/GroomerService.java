package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.Groomer;
import com.auca.pet_shop_management_system.domain.GroomingBay;
import com.auca.pet_shop_management_system.domain.PetType;
import com.auca.pet_shop_management_system.repository.GroomerRepository;
import com.auca.pet_shop_management_system.repository.GroomingBayRepository;
import com.auca.pet_shop_management_system.repository.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroomerService {

    @Autowired
    private GroomerRepository groomerRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private GroomingBayRepository groomingBayRepository;

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

    public List<Groomer> findByPetTypeName(String name) {
        return groomerRepository.findGroomersByPetTypeName(name);
    }

    @Transactional
    public String addPetType(UUID groomerId, UUID petTypeId) {
        Optional<Groomer> groomerOptional = groomerRepository.findById(groomerId);
        if (groomerOptional.isEmpty()) {
            return "The groomer with that id does not exist";
        }
        Optional<PetType> petTypeOptional = petTypeRepository.findById(petTypeId);
        if (petTypeOptional.isEmpty()) {
            return "That pet type does not exist";
        }
        Groomer groomer = groomerOptional.get();
        PetType petType = petTypeOptional.get();
        if (groomer.getPetTypes().contains(petType)) {
            return "The groomer already has that pet type";
        }
        groomer.getPetTypes().add(petType);
        groomerRepository.save(groomer);
        return "Pet type added to the groomer";
    }

    public String changeGroomingBay(UUID groomerId, int bayNumber) {
        Optional<Groomer> groomerOptional = groomerRepository.findById(groomerId);
        if (groomerOptional.isEmpty()) {
            return "The groomer with that id does not exist";
        }
        Optional<GroomingBay> bayOptional = groomingBayRepository.findByBayNumber(bayNumber);
        if (bayOptional.isEmpty()) {
            return "The grooming bay with that bayNumber does not exist";
        }
        Groomer groomer = groomerOptional.get();
        GroomingBay groomingBay = bayOptional.get();
        boolean alreadyInThatBay = groomer.getGroomingBay() != null
                && groomer.getGroomingBay().getId().equals(groomingBay.getId());
        if (!alreadyInThatBay && groomerRepository.existsByGroomingBay(groomingBay)) {
            return "That grooming bay is taken by another groomer";
        }
        groomer.setGroomingBay(groomingBay);
        groomerRepository.save(groomer);
        return "Grooming bay changed successfully";
    }
}