package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PetOwnerRepository extends JpaRepository<PetOwner, UUID> {

    List<PetOwner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}