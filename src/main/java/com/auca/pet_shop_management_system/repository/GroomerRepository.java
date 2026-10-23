package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.Groomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroomerRepository extends JpaRepository<Groomer, UUID> {

    @Query("SELECT DISTINCT g FROM Groomer g JOIN g.petTypes p WHERE LOWER(p.name) = LOWER(:name)")
    List<Groomer> findGroomersByPetTypeName(@Param("name") String name);
}