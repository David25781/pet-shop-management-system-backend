package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PetTypeRepository extends JpaRepository<PetType, UUID> {

    @Query("SELECT p FROM PetType p WHERE p.groomers IS EMPTY")
    List<PetType> findUnusedPetTypes();
}