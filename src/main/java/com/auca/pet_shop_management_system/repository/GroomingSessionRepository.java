package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroomingSessionRepository extends JpaRepository<GroomingSession, UUID> {
}