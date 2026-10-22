package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.Groomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroomerRepository extends JpaRepository<Groomer, UUID> {
}