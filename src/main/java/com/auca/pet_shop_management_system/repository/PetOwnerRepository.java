package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PetOwnerRepository extends JpaRepository<PetOwner, UUID> {

    List<PetOwner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT s.petOwner FROM GroomingSession s WHERE s.groomer.id = :groomerId")
    List<PetOwner> findPetOwnersByGroomerId(@Param("groomerId") UUID groomerId);
}