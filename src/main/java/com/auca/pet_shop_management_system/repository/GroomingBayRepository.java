package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.GroomingBay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GroomingBayRepository extends JpaRepository<GroomingBay, UUID> {

    @Query("SELECT b FROM GroomingBay b WHERE NOT EXISTS (SELECT g FROM Groomer g WHERE g.groomingBay = b) ORDER BY b.bayNumber")
    List<GroomingBay> findFreeGroomingBays();
}