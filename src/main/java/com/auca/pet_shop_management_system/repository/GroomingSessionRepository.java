package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GroomingSessionRepository extends JpaRepository<GroomingSession, UUID> {

    List<GroomingSession> findByStatusOrderBySessionDateAsc(SessionStatus status);

    List<GroomingSession> findBySessionDateBetweenOrderBySessionDateAsc(LocalDate start, LocalDate end);
}